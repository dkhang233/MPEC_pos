package com.pos.app.util;

import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;

// sử dụng JNA để lấy thư mục Downloads trên Windows
public class DownloadsFolderUtil {
    public static final Guid.GUID FOLDERID_Downloads = new Guid.GUID("{374DE290-123F-4565-9164-39C4925E467B}");

    public static String getDownloadsFolder() {
        PointerByReference outPath = new PointerByReference();
        WinNT.HRESULT result = Shell32.INSTANCE.SHGetKnownFolderPath(FOLDERID_Downloads, 0, null, outPath);
        if (WinNT.S_OK.equals(result)) {
            String path = outPath.getValue().getWideString(0);
            // Giải phóng bộ nhớ được cấp phát bởi hệ thống
            Ole32.INSTANCE.CoTaskMemFree(outPath.getValue());
            return path;
        }
        return null;
    }
}
