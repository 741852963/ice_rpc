// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `Nested.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Demo;

public interface NestedPrx extends Ice.ObjectPrx
{
    public void nestedCall(int level, NestedPrx proxy);

    public void nestedCall(int level, NestedPrx proxy, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy);

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy, Ice.Callback __cb);

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy, Callback_Nested_nestedCall __cb);

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy, java.util.Map<String, String> __ctx, Callback_Nested_nestedCall __cb);

    public void end_nestedCall(Ice.AsyncResult __result);
}