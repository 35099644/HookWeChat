/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/liulixin/work/android_project/myproject/Push/app/src/main/aidl/com/tensynchina/push/IPushServiceAidlInterface.aidl
 */
package com.tensynchina.push;
// Declare any non-default types here with import statements

public interface IPushServiceAidlInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.tensynchina.push.IPushServiceAidlInterface
{
private static final String DESCRIPTOR = "com.tensynchina.push.IPushServiceAidlInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.tensynchina.push.IPushServiceAidlInterface interface,
 * generating a proxy if needed.
 */
public static com.tensynchina.push.IPushServiceAidlInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.tensynchina.push.IPushServiceAidlInterface))) {
return ((com.tensynchina.push.IPushServiceAidlInterface)iin);
}
return new com.tensynchina.push.IPushServiceAidlInterface.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_sendMessage:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
String _arg1;
_arg1 = data.readString();
String _arg2;
_arg2 = data.readString();
String _arg3;
_arg3 = data.readString();
this.sendMessage(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.tensynchina.push.IPushServiceAidlInterface
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void sendMessage(String uuid, String key, String end_time, String data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(uuid);
_data.writeString(key);
_data.writeString(end_time);
_data.writeString(data);
mRemote.transact(Stub.TRANSACTION_sendMessage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_sendMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void sendMessage(String uuid, String key, String end_time, String data) throws android.os.RemoteException;
}
