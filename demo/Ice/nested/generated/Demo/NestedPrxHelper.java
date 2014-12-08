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

public final class NestedPrxHelper extends Ice.ObjectPrxHelperBase implements NestedPrx
{
    private static final String __nestedCall_name = "nestedCall";

    public void nestedCall(int level, NestedPrx proxy)
    {
        nestedCall(level, proxy, null, false);
    }

    public void nestedCall(int level, NestedPrx proxy, java.util.Map<String, String> __ctx)
    {
        nestedCall(level, proxy, __ctx, true);
    }

    private void nestedCall(int level, NestedPrx proxy, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "nestedCall", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __delBase = __getDelegate(false);
                    _NestedDel __del = (_NestedDel)__delBase;
                    __del.nestedCall(level, proxy, __ctx, __observer);
                    return;
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy)
    {
        return begin_nestedCall(level, proxy, null, false, null);
    }

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy, java.util.Map<String, String> __ctx)
    {
        return begin_nestedCall(level, proxy, __ctx, true, null);
    }

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy, Ice.Callback __cb)
    {
        return begin_nestedCall(level, proxy, null, false, __cb);
    }

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_nestedCall(level, proxy, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy, Callback_Nested_nestedCall __cb)
    {
        return begin_nestedCall(level, proxy, null, false, __cb);
    }

    public Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy, java.util.Map<String, String> __ctx, Callback_Nested_nestedCall __cb)
    {
        return begin_nestedCall(level, proxy, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_nestedCall(int level, NestedPrx proxy, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __nestedCall_name, __cb);
        try
        {
            __result.__prepare(__nestedCall_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeInt(level);
            NestedPrxHelper.__write(__os, proxy);
            __result.__endWriteParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public void end_nestedCall(Ice.AsyncResult __result)
    {
        __end(__result, __nestedCall_name);
    }

    public static NestedPrx checkedCast(Ice.ObjectPrx __obj)
    {
        NestedPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof NestedPrx)
            {
                __d = (NestedPrx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId()))
                {
                    NestedPrxHelper __h = new NestedPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static NestedPrx checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        NestedPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof NestedPrx)
            {
                __d = (NestedPrx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId(), __ctx))
                {
                    NestedPrxHelper __h = new NestedPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static NestedPrx checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        NestedPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId()))
                {
                    NestedPrxHelper __h = new NestedPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static NestedPrx checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        NestedPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId(), __ctx))
                {
                    NestedPrxHelper __h = new NestedPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static NestedPrx uncheckedCast(Ice.ObjectPrx __obj)
    {
        NestedPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof NestedPrx)
            {
                __d = (NestedPrx)__obj;
            }
            else
            {
                NestedPrxHelper __h = new NestedPrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static NestedPrx uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        NestedPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            NestedPrxHelper __h = new NestedPrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    public static final String[] __ids =
    {
        "::Demo::Nested",
        "::Ice::Object"
    };

    public static String ice_staticId()
    {
        return __ids[0];
    }

    protected Ice._ObjectDelM __createDelegateM()
    {
        return new _NestedDelM();
    }

    protected Ice._ObjectDelD __createDelegateD()
    {
        return new _NestedDelD();
    }

    public static void __write(IceInternal.BasicStream __os, NestedPrx v)
    {
        __os.writeProxy(v);
    }

    public static NestedPrx __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            NestedPrxHelper result = new NestedPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }

    public static final long serialVersionUID = 0L;
}