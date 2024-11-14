
/**
 * 任务类
 */
export class Task{
    /**
     * 构造函数
     * @param id 任务ID
     * @param name 任务名称
     */
    constructor(private id:number,private name:string,private description:string){
    }

    /**
     * 获取任务名称
     * @returns 任务名称
     */
    public getName(){
        return this.name;
    }

    public getId(){
        return this.id;
    }

    public getDescription(){
        return this.description;
    }

}