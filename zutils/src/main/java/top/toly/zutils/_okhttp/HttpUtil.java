package top.toly.zutils._okhttp;//package top.toly.zutils.core.io;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/25:9:58
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class HttpUtil {
    private String mUrl;
    private Map<String, String> mParam;

    private HttpResponse mHttpResponse;
    private OkHttpClient mHttpClient = new OkHttpClient();

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public interface HttpResponse {
        void onSuccess(String result);

        void onFail(String error);
    }

    public HttpUtil(HttpResponse httpResponse) {
        mHttpResponse = httpResponse;
    }

    public void sendPostHttp(String url, Map<String, String> param) {
        sendHttp(url, param, true);

    }


    public void sendGetHttp(String url, Map<String, String> param) {
        sendHttp(url, param, false);

    }

    private void sendHttp(String url, Map<String, String> param, boolean isPost) {
        mUrl = url;
        mParam = param;
        //获取网络数据
        getData(isPost);
    }

    /**
     * 获取网络数据
     *
     * @param isPost
     */
    private void getData(boolean isPost) {
        //创建request请求
        final Request request = createRequest(isPost);

        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mHttpResponse != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mHttpResponse.onFail("请求错误");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (mHttpResponse == null) {
                    return;
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!response.isSuccessful()) {
                            mHttpResponse.onFail("请求失败：code" + response);
                        } else {
                            try {
                                mHttpResponse.onSuccess(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

    }

    /**
     * @param isPost
     * @return
     */
    private Request createRequest(boolean isPost) {
        Request request;

        if (isPost) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            //遍历map请求参数
            Iterator<Map.Entry<String, String>> iterator = mParam.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            request = new Request.Builder().url(mUrl).post(builder.build()).build();

        } else {
            String urlStr = mUrl + "?" + MapParamToString(mParam);
            request = new Request.Builder().url(urlStr).build();
        }
        return request;
    }

    /**
     * @param param
     * @return
     */
    private String MapParamToString(Map<String, String> param) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = mParam.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        String str = sb.toString().substring(0, sb.length() - 1);
        return str;
    }
}
