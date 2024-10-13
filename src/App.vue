<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { imageToAscii, videoToAscii } from 'img-video-ascii';

async function loadImage() {
  const container = document.getElementById("ascii");
  const ptrDom = await imageToAscii("http://alaya.zone:8003/m_1941728520937_.pic.jpg", container, {
    fontSize: 1,
    lineHeight: 1
  });
  console.log("获取的图片数据：", ptrDom);
}

async function loadVideo() {
  const container = document.getElementById("ascii");
  const ptrDom = await videoToAscii("http://alaya.zone:8003/5.mp4", container);
}

onMounted(() => {
  loadImage();
  // loadVideo();
})

// 可以删除 HelloWorld 的导入，因为我们现在使用路由
</script>

<template>
  <div>
    <div id="ascii"></div>
    <router-view></router-view>
  </div>
</template>

<style scoped>
/* 您可以保留或修改现有的样式 */
pre {
  font-family: monospace;
  line-height: 6px;
  letter-spacing: 1px;
  white-space: pre-wrap;
}

#ascii {
  background-color: #fff;
  display: inline-block;
  /* 使用 inline-block 让内容决定尺寸 */
  border: 1px solid red;
  /* 可选边框用于查看内容尺寸变化 */
}
</style>
