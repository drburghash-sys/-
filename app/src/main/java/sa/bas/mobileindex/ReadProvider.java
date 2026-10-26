package sa.bas.mobileindex;
import android.content.*; import android.database.*; import android.net.Uri; import android.os.*; import android.provider.OpenableColumns; import android.webkit.MimeTypeMap; import java.io.*;
/** Exposes only read descriptors. Never offers writes, deletes or updates. */
public class ReadProvider extends ContentProvider {
 public boolean onCreate(){return true;}
 private File file(Uri u)throws FileNotFoundException {try {File root=Environment.getExternalStorageDirectory().getCanonicalFile(); File f=new File(u.getPath()).getCanonicalFile(); if(!f.getPath().startsWith(root.getPath()+"/")||!f.isFile())throw new IOException(); return f;}catch(IOException e){throw new FileNotFoundException();}}
 public ParcelFileDescriptor openFile(Uri u,String mode)throws FileNotFoundException {if(!"r".equals(mode))throw new FileNotFoundException("Read only");return ParcelFileDescriptor.open(file(u),ParcelFileDescriptor.MODE_READ_ONLY);}
 public String getType(Uri u){String ext=MimeTypeMap.getFileExtensionFromUrl(Uri.encode(u.getLastPathSegment()));String t=MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext.toLowerCase());return t==null?"application/octet-stream":t;}
 public Cursor query(Uri u,String[] p,String s,String[] a,String o){try{File f=file(u);String[] cols=p==null?new String[]{OpenableColumns.DISPLAY_NAME,OpenableColumns.SIZE}:p;MatrixCursor c=new MatrixCursor(cols);Object[] row=new Object[cols.length];for(int i=0;i<cols.length;i++)row[i]=cols[i].equals(OpenableColumns.DISPLAY_NAME)?f.getName():cols[i].equals(OpenableColumns.SIZE)?f.length():null;c.addRow(row);return c;}catch(Exception e){return null;}}
 public Uri insert(Uri u,ContentValues v){throw new UnsupportedOperationException("Read only");} public int delete(Uri u,String s,String[] a){throw new UnsupportedOperationException("Read only");} public int update(Uri u,ContentValues v,String s,String[] a){throw new UnsupportedOperationException("Read only");}
}
