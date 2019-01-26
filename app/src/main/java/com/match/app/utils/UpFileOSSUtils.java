package com.match.app.utils;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.match.app.config.BuildConfig;
import com.match.app.message.entity.UploadFile;

import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UpFileOSSUtils {

    private String bucketName;
    private static UpFileOSSUtils mInstance;

    public static UpFileOSSUtils getInstance() {
        if (mInstance == null) {
            synchronized (UpFileOSSUtils.class) {
                mInstance = new UpFileOSSUtils();
            }
        }
        return mInstance;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    private OSS getOSS(Context context) {
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(BuildConfig.STS_SERVER_URL);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // connction time out default 15s
        conf.setSocketTimeout(15 * 1000); // socket timeout，default 15s
        conf.setMaxConcurrentRequest(5); // synchronous request number，default 5
        conf.setMaxErrorRetry(2); // retry，default 2
        return new OSSClient(context, BuildConfig.endpoint, credentialProvider, conf);
    }

    /**
     * 图片上传
     *
     * @param context          上下文
     * @param uploadFile   图片的本地路径
     * @param onUploadListener 回调监听
     */
    public void upload(final Context context, final int position, final UploadFile uploadFile,
                       final OnUploadListener onUploadListener) {
        Observable.just(context)
                .map(new Function<Context, OSS>() {
                    @Override
                    public OSS apply(Context context) throws Exception {
                        return getOSS(context);
                    }
                })
                .map(new Function<OSS, String>() {
                    @Override
                    public String apply(OSS oss) throws Exception {
                        // 创建上传的对象
                        PutObjectRequest put = new PutObjectRequest(bucketName, BuildConfig.dir + getUUIDImage()
                                , uploadFile.getPath());
                        // 上传的进度回调
                        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                            @Override
                            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                                if (onUploadListener == null) {
                                    return;
                                }
                                onUploadListener.onProgress(position, currentSize, totalSize);
                            }
                        });
                        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                            @Override
                            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                                if (onUploadListener == null) {
                                    return;
                                }
                                onUploadListener.onSuccess(position, uploadFile.getPath(), BuildConfig.prefix + request.getObjectKey());
                            }

                            @Override
                            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                                serviceException.printStackTrace();
                                clientException.printStackTrace();
                                if (onUploadListener == null) {
                                    return;
                                }
                                onUploadListener.onFailure(position);
                            }
                        });
                        return uploadFile.getPath();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public interface OnUploadListener {
        /**
         * 上传的进度
         */
        void onProgress(int position, long currentSize, long totalSize);

        /**
         * 成功上传
         */
        void onSuccess(int position, String uploadPath, String ossUrl);

        /**
         * 上传失败
         */
        void onFailure(int position);
    }

    /**
     * 上传到后台的图片的名称
     */
    private String getUUIDImage() {
        UUID uuid = UUID.randomUUID();
        StringBuilder fileName = new StringBuilder();
        fileName.append(uuid.toString());
        fileName.append(String.valueOf(System.currentTimeMillis()));
        return fileName + ".png";
    }
}
