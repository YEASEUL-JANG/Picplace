<template>
    <div id="map"
         style="width:100%;"
         :style="{'height':hsize+'px'}"
         >
    </div>
</template>

<script>
import {onMounted, ref} from "vue";

export default {
    name: "PlaceMap",
    props: {
        height: Number
    },
    setup(props,context) {
        const hsize = ref(props.height);
        onMounted( () => {
            if(window.kakao && window.kakao.maps){
                context.emit("onSearch")
            }else {
                loadScript();
            }
        })
        const loadScript = () => {
            const script = document.createElement("script")
            script.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=cecdc2b9460792373bea4e3003d3d9ae";
            script.onload = () => window.kakao.maps.load(()=>{context.emit("onSearch");});
            document.head.appendChild(script) //html>head 안에 스크립트를 추가
        }




        return{
            hsize,

        }
    }
}
</script>

<style scoped>

</style>