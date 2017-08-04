package com.android.setuper;

import android.content.pm.PackageManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.v4.content.ContextCompat;

import junit.framework.Assert;

import java.io.IOException;

/**
 * Created by guohao4 on 2017/8/4.
 * Email: Tornaco@163.com
 */

public class TestSetuper {

    public static void grantPermissions(String packageName, String permission) throws IOException {
        Assert.assertNotNull(packageName);
        Assert.assertNotNull(permission);
        String cmd = String.format("pm grant %s %s", packageName, permission);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
                .executeShellCommand(cmd);
        Assert.assertTrue("Permission not granted:" + permission,
                ContextCompat.checkSelfPermission(InstrumentationRegistry.getTargetContext()
                        , permission) == PackageManager.PERMISSION_GRANTED);
    }
}
