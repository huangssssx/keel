//防抖搜索
export const debouncedSearch = <T extends (...args:any[])=>any>(searchFun:T)=>{
    let _instance: ReturnType<typeof setTimeout> | undefined = undefined;
    return function(...args:Parameters<T>){
        if(_instance!==-1){
            clearTimeout(_instance);
        }
        _instance = setTimeout(()=>{
            searchFun(...args);
        },500);
    }
}