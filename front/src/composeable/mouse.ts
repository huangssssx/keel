import { Position } from "@element-plus/icons-vue";
import { onMounted ,onUnmounted, Ref, reactive, toRefs} from "vue"

export type Position = {
    x:Ref<number>,
    y:Ref<number>
}


//鼠标相关
export const useMousePosition = ():Position => {
    const position = reactive({x:0,y:0});

    const update = (event:MouseEvent)=>{
        position.x = event.pageX;
        position.y = event.pageY;
    }

    onMounted(() => {
        window.addEventListener("mousemove",update);
    })

    onUnmounted(() => {
        window.removeEventListener("mousemove",update);
    })

    return toRefs( position);
}

