package com.match.app.ui.info;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;
import com.match.app.common.User;
import com.match.app.ui.login.ImageChoiceDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*******
 * 修改用户信息
 * create by john
 */
public class ModifyActivity extends BaseActivity {
    private static final int RESULT_IMAGE = 100; // 打开相册
    private static final int CROP_PICTURE = 101;
    private static final int RESULT_CAMERA = 200;

    @BindView(R.id.img_bg_colum)
    ImageView imgBgColum;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_age_position)
    TextView tvAgePosition;
    @BindView(R.id.tv_credit_score)
    TextView tvCreditScore;
    @BindView(R.id.gv_current_record)
    GridView gvCurrentRecord;
    @BindView(R.id.gv_past_record)
    GridView gvPastRecord;
    @BindView(R.id.img_back)
    ImageView imgBack;

    private Uri cropImageUri;
    private Uri imageUri;
    private int action;
    private String bgUrl;
    private User user;
    private List<Person> currents;
    private List<Person> befores;
    private RecordListAdapter currentAdapter;
    private RecordListAdapter beforeAdapter;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_modify);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        getData();
        user = User.getInstance();
        tvName.setText(user.getName());
        tvAgePosition.setText("24.广州");
        tvCreditScore.setText("信用值：72分");
        Glide.with(mContext)
                .load(bgUrl)
                .placeholder(R.mipmap.anim_avitor)
                .error(R.mipmap.anim_avitor)
                .dontAnimate()
                .into(imgBgColum);
        tvName.setText(User.getInstance().getName());

        imgBgColum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ImageChoiceDialog.Builder(mContext)
                        .setCancelable(false)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                action = view.getId();
                                switch (view.getId()) {
                                    case R.id.tv_photo:
                                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                                            photo();
                                            return;
                                        }

                                        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                                PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions((Activity) mContext, new String[]{
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            }, 1);
                                        } else {
                                            photo();
                                        }
                                        break;
                                    case R.id.tv_select:
                                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                                            openAlbum();
                                            return;
                                        }

                                        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                                PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions((Activity) mContext, new String[]{
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            }, 1);
                                        } else {
                                            openAlbum();
                                        }
                                        break;
                                }
                            }
                        }).create();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gvCurrentRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        gvPastRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    }

    /****
     * 获取数据
     */
    private void getData() {
        currents = new ArrayList<>();
        befores = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            currents.add(new Person("测试" + i, ""));
            befores.add(new Person("测试" + i, "", true));
        }
        gvCurrentRecord.setNumColumns(4);
        gvPastRecord.setNumColumns(4);
        currentAdapter = new RecordListAdapter(mContext, currents);
        beforeAdapter = new RecordListAdapter(mContext, befores);
        gvCurrentRecord.setAdapter(currentAdapter);
        gvPastRecord.setAdapter(beforeAdapter);

        setListViewHeightBasedOnChildren(gvCurrentRecord);
        currentAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(gvPastRecord);
        beforeAdapter.notifyDataSetChanged();
    }

    public void setListViewHeightBasedOnChildren(GridView view) {
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
        currentAdapter.notifyDataSetChanged();
    }

    private void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //系统常量， 启动相机的关键
        startActivityForResult(openCameraIntent, RESULT_CAMERA);
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_IMAGE);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    switch (action) {
                        case R.id.tv_select:
                            openAlbum();
                            break;
                        case R.id.tv_photo:
                            photo();
                            break;
                    }

                } else {
                    Toast.makeText(this, "你没有开启权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_IMAGE:
            case RESULT_CAMERA:
                if (resultCode == RESULT_OK && data != null) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4 及以上系统使用这个方法处理图片
                        handlerImageOnKikat(data);
                    } else {
                        // 4.4 一下系统使用这个方法处理图片
                        handlerImageBeforeKitkat(data);
                    }
                }
                break;
            case CROP_PICTURE:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap headShot = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }


                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handlerImageOnKikat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的URI，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        startPhotoZoom(uri);
    }

    public void startPhotoZoom(Uri uri) {
        File CropPhoto = new File(getExternalCacheDir(), "crop_image.jpg");
        try {
            if (CropPhoto.exists()) {
                CropPhoto.delete();
            }
            CropPhoto.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cropImageUri = Uri.fromFile(CropPhoto);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);

        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CROP_PICTURE);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void handlerImageBeforeKitkat(Intent data) {
        Uri cropUri = data.getData();
        startPhotoZoom(cropUri);
    }

}
