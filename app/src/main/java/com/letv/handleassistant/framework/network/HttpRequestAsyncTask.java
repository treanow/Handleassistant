package com.letv.handleassistant.framework.network;

import android.os.AsyncTask;

import com.letv.handleassistant.constant.Constant;
import com.letv.handleassistant.framework.bean.BaseResponse;
import com.letv.handleassistant.framework.bean.DataHull;
import com.letv.handleassistant.framework.parser.BaseParser;
import com.letv.handleassistant.utils.LogUtil;



public class HttpRequestAsyncTask<T extends BaseResponse> extends AsyncTask<Request, Void, DataHull<T>> {
    private static final int RESPONSE_TIME_OUT = 60000;
    private static final int REQUEST_TIME_OUT = 60000;

    private String resultString;
    private OnCompleteListener<T> onCompleteListener;
    private BaseParser<T> parser;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected DataHull<T> doInBackground(Request... params) {
//        String serverAddress = SoftApplication.softApplication.getAppInfo().serverAddress;
//        String result = "";
//        String PREFIX = "--";
//        String LINEND = "\r\n";
//        String CHARSET = "UTF-8";
//        String MULTIPART_FROM_DATA = "multipart/form-data";
//        String BOUNDARY = java.util.UUID.randomUUID().toString();
//        Request request = params[0];
//        try {
//            LogUtil.log("请求地址:" + serverAddress + request.getServerInterfaceDefinition().getOpt());
//            URL url = new URL(serverAddress + request.getServerInterfaceDefinition().getOpt());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            conn.setUseCaches(false);
//            conn.setConnectTimeout(REQUEST_TIME_OUT);
//            conn.setReadTimeout(RESPONSE_TIME_OUT);
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("Charset", "UTF-8");
//            conn.setRequestProperty("Content-Type", "application/json");
//            //打印传给后台的参数
//            LogUtil.log(request.getRequestJson());
//            // 首先组拼文本类型的参数
//            conn.connect();
//            OutputStreamWriter out = new OutputStreamWriter(
//                    conn.getOutputStream(), "UTF-8"); // utf-8编码
//            out.append(request.getRequestJson());
//            out.flush();
//            out.close();
//
//            int responseCode = conn.getResponseCode();
//            if (responseCode != 200) {
//                LogUtil.log("返回responseCode：" + responseCode);
//                return null;
//            }
//
//            InputStream in = conn.getInputStream();
//            InputStreamReader isReader = new InputStreamReader(in, CHARSET);
//            BufferedReader bufReader = new BufferedReader(isReader);
//            result = bufReader.readLine();
//            resultString = result;
//            LogUtil.log("返回result：" + result);
//            conn.disconnect();
//            return (DataHull) parser.getParseResult(resultString);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    protected void onPostExecute(DataHull<T> result) {
        super.onPostExecute(result);
        if (null != onCompleteListener) {
            if (null == result) {
                LogUtil.log("请求异常");
                onCompleteListener.onPostFail();
                onCompleteListener.onCompleted(null, resultString);
                return;
            }
            onCompleteListener.onCompleted(result.dataEntry, resultString);
            if (result.dataEntry == null) {
                LogUtil.log("解析失败");
                return;
            }
            if (result.dataEntry.errCode != Constant.ERROR_CODE_OK) {
                LogUtil.log("返回状态码不为0");
                onCompleteListener.onCodeError(result.dataEntry);
                return;
            }
            LogUtil.log("请求成功");
            onCompleteListener.onSuccessed(result.dataEntry, resultString);
        }
    }

    public OnCompleteListener<T> getOnCompleteListener() {
        return onCompleteListener;
    }

    public void setOnCompleteListener(OnCompleteListener<T> onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public void setParser(BaseParser<T> parser) {
        this.parser = parser;
    }

}
