//package top.toly.zutils._okhttp;//package top.toly.zutils.core.io;
//
//import android.text.TextUtils;
//
//
//import top.toly.zutils.core.shortUtils.LogUtils;
//import top.toly.zutils.core.io.CacheUtil_File;
//
///**
// * 作者：张风捷特烈
// * 时间：2018/3/29:10:21
// * 邮箱：1981462002@qq.com
// * 说明：网络封装，连接网络获取数据，并写缓存
// */
//public abstract class GetHttpDataWithCach<T> {
//
//    private String mUrl;
//
//    public GetHttpDataWithCach(String url) {
//        mUrl = url;
//        String cache = CacheUtil_File.getCache(mUrl);//读缓存
//
//        if (!TextUtils.isEmpty(cache)) {//如果有缓存
//            getDataByGson(cache);//解析数据抽象方法
//
//        }
//
//        getDataFromService();//连接网络获取数据，并写缓存
//    }
//
//    /**
//     * 从网络获取数据
//     */
//    private void getDataFromService() {
//        OkHttpUtils.get().url(mUrl).build().execute(new StringCallback() {
//            /**
//             * 成功时回调
//             * @param response
//             * @param id
//             */
//            @Override
//            public void onResponse(String response, int id) {
//                if (response != null) {
//                    getDataByGson(response);
//                }
//                CacheUtil_File.setCache(mUrl, response);//写缓存
//            }
//
//            /**
//             * 网络错误时回调
//             * @param call
//             * @param e
//             * @param id
//             */
//            @Override
//            public void onError(okhttp3.Call call, Exception e, int id) {
//                LogUtils.e("网络错误");
//                if (CacheUtil_File.getCache(mUrl) != null) {
//                    getDataByGson(CacheUtil_File.getCache(mUrl));
//                }else{
//                    error();
//                }
//
//            }
//
//        });
//    }
//
//    public abstract void getDataByGson(String response);
//    public abstract void error();
//
//
//
//}
