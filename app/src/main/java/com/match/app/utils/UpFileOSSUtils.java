package com.match.app.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.match.app.message.entity.UploadFile;

import java.util.Calendar;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UpFileOSSUtils {
    //oss
    private String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    private String accessKeyId = "LTAIjWN1v5qFDuZJ";
    private String accessKeySecret = "4tbI6RNsQhSQplMncLRpAoSxh7Enq4";
    //    public static String ossHead = "https://testpr.oss-cn-shenzhen.aliyuncs.com/";
    public static String logoBucketName = "testpr";
    public static String logoObjectHead = "logo";

    private String bucketName;
    private String objectKeyHead;
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

    public void setObjectKeyHead(String objectKeyHead) {
        this.objectKeyHead = objectKeyHead;
    }

    private OSS initOSS(Context context) {
        OSSCredentialProvider credentialProvider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                return OSSUtils.sign(accessKeyId, accessKeySecret, content);
            }
        };
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // connction time out default 15s
        conf.setSocketTimeout(15 * 1000); // socket timeout，default 15s
        conf.setMaxConcurrentRequest(5); // synchronous request number，default 5
        conf.setMaxErrorRetry(2); // retry，default 2
        return new OSSClient(context, endpoint, credentialProvider, conf);
    }

    /**
     * 图片上传
     *
     * @param context          上下文
     * @param uploadFile       图片的本地路径
     * @param onUploadListener 回调监听
     */
    public void upload(final Context context, final int position, final UploadFile uploadFile,
                       final OnUploadListener onUploadListener) {

        Observable.just(context)
                .map(new Function<Context, OSS>() {
                    @Override
                    public OSS apply(Context context) throws Exception {
                        return initOSS(context);
                    }
                })
                .map(new Function<OSS, String>() {
                    @Override
                    public String apply(OSS oss) throws Exception {
                        // 创建上传的对象
                        PutObjectRequest put = new PutObjectRequest(bucketName, objectKeyHead + getUUIDImage()
                                , uploadFile.getPath());
                        // 上传的进度回调
                        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                            @Override
                            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                                int progress = (int) (100 * currentSize / totalSize);
                                if (onUploadListener != null) {
                                    onUploadListener.onProgress(position, progress);
                                }
                            }
                        });
                        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                            @Override
                            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                                if (onUploadListener != null) {
                                    onUploadListener.onSuccess(position, uploadFile.getPath(), request.getObjectKey());
                                }
                            }

                            @Override
                            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                                if (clientException != null) {
                                    // 本地异常如网络异常等
                                    clientException.printStackTrace();
                                }
                                if (serviceException != null) {
                                    // 服务异常
                                    Log.e("ErrorCode", serviceException.getErrorCode());
                                    Log.e("RequestId", serviceException.getRequestId());
                                    Log.e("HostId", serviceException.getHostId());
                                    Log.e("RawMessage", serviceException.getRawMessage());
                                }
                                if (onUploadListener != null) {
                                    onUploadListener.onFailure();
                                }
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
        void onProgress(int position, int progress);

        /**
         * 成功上传
         */
        void onSuccess(int position, String uploadPath, String objectKey);

        /**
         * 上传失败
         */
        void onFailure();
    }

    /**
     * 上传到后台的图片的名称
     */
    private String getUUIDImage() {
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH) + 1;

        UUID uuid = UUID.randomUUID();
        StringBuilder fileName = new StringBuilder();
        fileName.append("/")
                .append(mYear)
                .append("/")
                .append(mMonth)
                .append("/")
//                .append(uuid.toString())
//                .append("_")
                .append(String.valueOf(System.currentTimeMillis()));
        return fileName + ".png";
    }
}
