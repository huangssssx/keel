//代理组合

export const proxyObject =(obj:Record<string, any>)=>{
    return new Proxy(obj,{
        get(target, p: string ) {
            // return Reflect.get(target,p,receiver)   
            // return Reflect.get(target, p);
            return target[p]
        },
    });
}