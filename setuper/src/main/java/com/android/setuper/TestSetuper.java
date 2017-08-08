package com.android.setuper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import junit.framework.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guohao4 on 2017/8/4.
 * Email: Tornaco@163.com
 */

public abstract class TestSetuper {

    public static void grantPermissions(String permission) throws IOException {
        grantPermissions(InstrumentationRegistry.getTargetContext().getPackageName(), permission);
    }

    public static void grantPermissions(String packageName, String permission) throws IOException {
        Assert.assertNotNull(packageName);
        Assert.assertNotNull(permission);
        String cmd = String.format("pm grant %s %s", packageName, permission);
        String res = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
                .executeShellCommand(cmd);
        Assert.assertTrue(res == null || !res.contains("Err"));
    }

    public static void grantAllDeclearedPermissions(String packageName) throws IOException {
        String[] permissions = getPkgInfo(InstrumentationRegistry.getTargetContext(), packageName).requestedPermissions;
        permissions = extractUnGranted(InstrumentationRegistry.getTargetContext(), packageName, permissions);
        for (String p : permissions) {
            grantPermissions(packageName, p);
        }
    }

    private static String[] extractUnGranted(Context context, String packageName, String[] declaredPerms) {
        if (declaredPerms == null || declaredPerms.length == 0) return null;
        PackageManager packageManager = context.getPackageManager();
        List<String> requestList = new ArrayList<>(declaredPerms.length);
        for (String info : declaredPerms) {
            int code = packageManager.checkPermission(info, packageName);
            if (code == PackageManager.PERMISSION_GRANTED) continue;
            requestList.add(info);
        }
        String[] out = new String[requestList.size()];
        for (int i = 0; i < requestList.size(); i++) {
            out[i] = requestList.get(i);
        }
        return out;
    }

    private static PackageInfo getPkgInfo(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void setStayAwake() throws IOException {
        String cmd = String.format("settings put global stay_on_while_plugged_in %s", 3);
        String res = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
                .executeShellCommand(cmd);
        Assert.assertTrue(res == null || !res.contains("Err"));
    }
}
