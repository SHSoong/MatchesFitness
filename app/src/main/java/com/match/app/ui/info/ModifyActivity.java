package com.match.app.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
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
import com.match.app.message.bean.B200Request;
import com.match.app.message.bean.BaseResponse;
import com.match.app.message.entity.Person;
import com.match.app.message.entity.UploadFile;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.ui.adapter.RecordListAdapter;
import com.match.app.utils.DateUtils;
import com.match.app.utils.ToastUtils;
import com.match.app.utils.UpFileOSSUtils;
import com.matches.fitness.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*******
 * 修改用户信息
 * create by john
 */
public class ModifyActivity extends BaseActivity {

    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;

    @BindView(R.id.arcImageView)
    ArcImageView arcImageView;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.ivEdit)
    TextView ivEdit;
    @BindView(R.id.ivConform)
    TextView ivConform;
    @BindView(R.id.tvAge)
    TextView tvAge;

    @BindView(R.id.currentRecord)
    GridView currentRecord;
    @BindView(R.id.beforeRecord)
    GridView beforeRecord;

    private List<Person> currents;
    private List<Person> befores;
    private RecordListAdapter currentAdapter;
    private RecordListAdapter beforeAdapter;

    private Boolean editEnable = false;
    private UploadFile file;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_modify);
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
        Glide.with(this)
                .load(User.getInstance().getLogo())
                .apply(new RequestOptions().placeholder(R.mipmap.avatar_bg_icon))
                .into(arcImageView);
        tvName.setText(User.getInstance().getName());
        tvAge.setText(String.valueOf(DateUtils.getAge(DateUtils.parse(User.getInstance().getBirthday()))));
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });
        ivConform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file == null) {
                    edit();
                } else {
                    uploadImage();
                }
            }
        });

        arcImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editEnable) {
                    PictureSelector.create(ModifyActivity.this)
                            .openGallery(PictureMimeType.ofImage())
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
            }
        });

        currentRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Person person = (Person) currentAdapter.getItem(i);
                Intent intent = new Intent(mContext, OtherPersonInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(OtherPersonInfoActivity.INFO_KEY, person);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        beforeRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Person person = (Person) beforeAdapter.getItem(i);
                Intent intent = new Intent(mContext, OtherPersonInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(OtherPersonInfoActivity.INFO_KEY, person);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        initAdapter();
    }

    private MaterialDialog mLoadingDialog;

    private void initDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new MaterialDialog.Builder(this)
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

    public void uploadImage() {
        initDialog();
        showDialog();
        UpFileOSSUtils.getInstance().setBucketName(UpFileOSSUtils.logoBucketName);
        UpFileOSSUtils.getInstance().setObjectKeyHead(UpFileOSSUtils.logoObjectHead);
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
                                initCallB200();
                            }

                            @Override
                            public void onFailure() {
                                dismissDialog();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToast(ModifyActivity.this, "上传失败");
                                    }
                                });
                            }
                        });
    }

    private void edit() {
        if (editEnable) {
            ivEdit.setVisibility(View.VISIBLE);
            ivConform.setVisibility(View.GONE);
        } else {
            ivConform.setVisibility(View.VISIBLE);
            ivEdit.setVisibility(View.GONE);
        }

        editEnable = !editEnable;
    }

    public void initCallB200() {
        B200Request request = new B200Request();
        request.setName(User.getInstance().getName());
        request.setBirthday(User.getInstance().getBirthday());
        request.setSex(User.getInstance().getSex());
        request.setHasExp(User.getInstance().getHasExp());
        request.setLogo(User.getInstance().getLogo());

        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doB200Request(request)
                .compose(RxSchedulers.<BaseResponse>io_main())
                .subscribe(new BaseObserver<BaseResponse>() {
                    @Override
                    protected void onHandleSuccess(BaseResponse res) {
                        if (res.isSuccess()) {
                            edit();
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(mContext, msg);
                    }
                });
    }

    private void initAdapter() {
        currents = new ArrayList<>();
        currentRecord.setNumColumns(4);
        currentAdapter = new RecordListAdapter(mContext, currents);
        currentRecord.setAdapter(currentAdapter);
        setListViewHeightBasedOnChildren(currentRecord, currentAdapter);
        currentAdapter.notifyDataSetChanged();

        befores = new ArrayList<>();
        beforeRecord.setNumColumns(4);
        beforeAdapter = new RecordListAdapter(mContext, befores);
        beforeRecord.setAdapter(beforeAdapter);
        setListViewHeightBasedOnChildren(beforeRecord, beforeAdapter);
        beforeAdapter.notifyDataSetChanged();
    }

    public void setListViewHeightBasedOnChildren(GridView view, RecordListAdapter adapter) {
        // 获取listview的adapter
        ListAdapter listAdapter = view.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, view);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = view.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        view.setLayoutParams(params);
        adapter.notifyDataSetChanged();
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
