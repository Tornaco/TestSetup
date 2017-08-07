package com.android.setuper;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import junit.framework.Assert;

import java.io.IOException;

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

    public static void setStayAwake() throws IOException {
        String cmd = String.format("settings put global stay_on_while_plugged_in %s", 3);
        String res = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
                .executeShellCommand(cmd);
        Assert.assertTrue(res == null || !res.contains("Err"));
    }
}
