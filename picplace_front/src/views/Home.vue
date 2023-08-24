<template>
  <!-- Page header with logo and tagline-->
    <header class="py-5 border-bottom mb-4" id="mainImage">
            <div class="text-center my-5" >
                <h1 class="fw-bolder">Let's Pick your Place !</h1>
                <p class="lead mb-0">사당의 맛집과 카페를 찜해보세요!</p>

            </div>
    </header>
  <!-- Page content-->
    <div class="container">
            <!-- Blog entries-->
                <div class="mb-5 mt-3">
            <h4>카페 Best</h4>
            <!-- place 목록-->
                <el-row  gutter=16>
                <el-col
                    v-for="(place,index) in cafes"
                    :key="index"
                    :span="8"
                    class="el-col"
                >
                    <el-card :body-style="{ padding: '0px', cursor:'pointer' }"
                             shadow="hover"
                             @click="placeDetail(place.placeId)">
                        <img :src="'./upload/'+place.placePhotos[0]"
                             class="image" style="width:100%;height:350px;"
                        />
                        <div style="padding: 10px">
                            <span>{{ place.name }}</span>
                            <el-tag class="ms-2" type="warning">{{getMenuType(place.menuType)}}</el-tag>
                            <div class="bottom">
                                <time class="time">{{ place.address }}</time>
                                <el-button
                                    :icon="Star" type="warning"  @click.stop="addPicplace(place.placeId)" class="ms-1">찜</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-col>
                </el-row>
                </div>
                <div class="mb-5">
            <h4>식당 Best</h4>
            <!-- place 목록-->
                <el-row  gutter=16>
                <el-col
                v-for="(res,index) in places"
                :key="index"
                :span="8"
                class="el-col"
                >
                <el-card :body-style="{ padding: '0px', cursor:'pointer' }"
                         shadow="hover"
                         @click="placeDetail(res.placeId)">
                    <img :src="'./upload/'+res.placePhotos[0]"
                         class="image" style="width:100%;height:350px;"
                    />
                    <div style="padding: 10px">
                        <span>{{ res.name }}</span>
                        <el-tag class="ms-2" type="warning">{{getMenuType(res.menuType)}}</el-tag>
                        <div class="bottom">
                            <time class="time">{{ res.address }}</time>
                            <el-button :icon="Star" type="warning"  @click.stop="addPicplace(res.placeId)" class="ms-1">찜</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-col>
                </el-row>
                </div>
            </div>

</template>
<script>

import {computed, onMounted, ref} from "vue";
import {Search, Star} from "@element-plus/icons-vue";
import axios from "@/common/axios";
import {useToast} from "@/common/toast";
import store from "@/store";
import {useRouter} from "vue-router";
import menuTypes from "@/model/menuType";
export default {
    computed: {
        Star() {
            return Star
        },
        Search() {
            return Search
        }
    },
    setup(){
        const loading = ref(false);
        const currentUser = computed(() => store.getters["getcurrentUser"]);
        const router = useRouter();
        const places = ref([]);
        const {triggerToast} = useToast();
        const cafes = ref([]);
        const searchthing = ref({
            placeName: '',
            menuKeyword: '',
            address: '',
            placeType: '',
            menuType:'',
            pageNum: 1
        })
        onMounted(() => {
            getMainPlace();
        })

        const getMainPlace =async () => {
            loading.value = true;
            try {
                const res = await axios.post('api/place/placelist',  searchthing.value);
                console.log("##placeList",res.data.data)
                for(const place of res.data.data){
                    if(place.placeType == "CAFE"){
                        if(cafes.value.length >= 6){
                            continue;
                        }
                        cafes.value.push(place)
                    }else{
                        if(places.value.length >= 6){
                            continue;
                        }
                        places.value.push(place)
                    }
                }
                console.log("##cafes",cafes.value)
                console.log("##restaurant",places.value)

                loading.value = false;

            }catch (err){
                console.log(err);
            }
        }

        const addPicplace = async (placeId) => {
            try {
                const userid = currentUser.value.userId;
                console.log("유저아이디 : " + userid + " place아이디 : " + placeId);
                const res = await axios.post("api/place/placePic/" + placeId, {
                    userId: userid
                });
                console.log("찜데이터",res.data)
                if (res.data.code == 200) {
                    triggerToast("찜목록에 추가되었습니다.");
                }
            } catch (err) {
                if (err?.response?.status === 409) {
                    triggerToast("이미 찜한 장소입니다.", "danger");
                } else {
                    triggerToast("Unexpected error occurred", "danger");
                }
                console.log(err);
            }
        }
        const placeDetail =(placeId) => {
            router.push("/detailPlace/"+placeId);
        }
        const getMenuType = (mtype) => {
            switch (mtype){
                case menuTypes.KOREAN.text:  return menuTypes.KOREAN.label;
                case menuTypes.WESTERN.text: return menuTypes.WESTERN.label;
                case menuTypes.JAPANESE.text: return menuTypes.JAPANESE.label;
                case menuTypes.CHINESE.text: return menuTypes.CHINESE.label;
                default: return menuTypes.ETC.label;
            }
        }


        return{
            getMainPlace,
            addPicplace,
            placeDetail,
            places,
            cafes,
            getMenuType



        }
    }

}
</script>
<style scoped>
#mainImage{
    background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ),url("/public/upload/main.jpg");

    width: 100%;
    height: 100%;
    color: white;
}
.bottom {
    margin-top: 13px;
    line-height: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
</style>