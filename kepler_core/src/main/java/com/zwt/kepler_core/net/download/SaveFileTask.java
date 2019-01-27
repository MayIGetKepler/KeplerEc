package com.zwt.kepler_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.zwt.kepler_core.application.Kepler;
import com.zwt.kepler_core.net.callback.IRequest;
import com.zwt.kepler_core.net.callback.ISuccess;
import com.zwt.kepler_core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * @author ZWT
 */
public class SaveFileTask extends AsyncTask<Object,Void,File> {

    private final ISuccess mISuccess;
    private final IRequest mIRequest;

    public SaveFileTask(ISuccess ISuccess, IRequest IRequest) {
        mISuccess = ISuccess;
        mIRequest = IRequest;
    }

    @Override
    protected File doInBackground(Object... objects) {
        String downloadDir = (String) objects[0];
        String extension = (String) objects[1];
        final String fullname = (String) objects[2];
        final ResponseBody responseBody = (ResponseBody) objects[3];
        final InputStream inputStream = responseBody.byteStream();
        if (downloadDir == null ||downloadDir.equals("")){
            downloadDir = "downloads";
        }
        if (extension == null ||extension.equals("")){
            extension = "";
        }
        if (fullname == null){
           return FileUtil.writeToDisk(inputStream,downloadDir,extension.toUpperCase(),extension);
        }else {
           return FileUtil.writeToDisk(inputStream,downloadDir,fullname);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (mISuccess != null){
            mISuccess.onSuccess(file.getPath());
        }
        if (mIRequest != null){
            mIRequest.onRequestEnd();
        }
        autoInstallApk(file);
    }

    private void autoInstallApk(File file){
        if (FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            Kepler.getApplicationContext().startActivity(install);
        }
    }
}
