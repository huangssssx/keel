export class ProxyObject {
    static createProxy<T extends Object>(sourceObject:T ) {

        return new Proxy(sourceObject,{
            get:(obj,prop)=>{
                console.log(`${obj}被读取：`,prop);
                return Reflect.get(obj,prop);
            },
            set:(obj,prop,value)=>{
                console.log(`${obj}被赋值：`,prop,value);
                return Reflect.set(obj,prop,value);
            }
        })
    }
}