package com.match.app.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.match.app.base.BaseActivity;
import com.match.app.common.User;
import com.match.app.customer.ArcImageView;
import com.match.app.message.bean.B005Request;
import com.match.app.message.bean.B005Response;
import com.match.app.message.entity.UploadFile;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.ui.home.activity.MainActivity;
import com.match.app.utils.DateUtils;
import com.match.app.utils.ToastUtils;
import com.match.app.utils.UpFileOSSUtils;
import com.matches.fitness.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AvatarActivity extends BaseActivity {

    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;
    @BindView(R.id.arcImageView)
    ArcImageView arcImageView;
    @BindView(R.id.tvUploadAvatar)
    TextView tvUploadAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAge)
    TextView tvAge;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    private UploadFile file;
    private MaterialDialog mLoadingDialog;

    private void initDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new MaterialDialog.Builder(AvatarActivity.this)
                    .content("上传中...")
                    .progress(false, 100, false)
                    .cancelable(true)
                    .build();
        }
    }

    private void showDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    private void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_avatar);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvName.setText(User.getInstance().getName());
        tvAge.setText(String.valueOf(DateUtils.getAge(DateUtils.parse(User.getInstance().getBirthday()))));
        tvUploadAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(AvatarActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file == null) {
                    ToastUtils.showToast(AvatarActivity.this, "请选择图片");
                    return;
                }
                uploadImage();
            }
        });
    }

    public void uploadImage() {
        initDialog();
        showDialog();
        UpFileOSSUtils.getInstance().setBucketName("testpr");
        UpFileOSSUtils.getInstance().setObjectKeyHead("logo");
        UpFileOSSUtils.getInstance()
                .upload(this, 0,
                        file, new UpFileOSSUtils.OnUploadListener() {
                            @Override
                            public void onProgress(int position, int progress) {
                                mLoadingDialog.setProgress(progress);
                            }

                            @Override
                            public void onSuccess(int position, String uploadPath, String objectKey) {
                                User.getInstance().setLogo(objectKey);
                                User.getInstance().save();
                                dismissDialog();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToast(AvatarActivity.this, "上传成功");
                                    }
                                });

//                                initCallB005();
                            }

                            @Override
                            public void onFailure() {
                                dismissDialog();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToast(AvatarActivity.this, "上传失败");
                                    }
                                });
                            }
                        });
    }

    public void initCallB005() {
        B005Request request = new B005Request();
        request.setName(User.getInstance().getName());
        request.setBirthday(User.getInstance().getBirthday());
        request.setSex(User.getInstance().getSex());
        request.setHasExp(User.getInstance().getHasExp());
        request.setLogo(User.getInstance().getLogo());

        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doPerfeect(request)
                .compose(RxSchedulers.<B005Response>io_main())
                .subscribe(new BaseObserver<B005Response>() {
                    @Override
                    protected void onHandleSuccess(B005Response res) {
                        if (res.isSuccess()) {
                            startActivity(new Intent(mContext, MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(mContext, msg);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    file = new UploadFile();
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        file.setPath(media.getPath());
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.color.black)
                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(mContext)
                                .load(file.getPath())
                                .apply(options)
                                .into(arcImageView);
//                        tvUploadAvatar.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }


}
