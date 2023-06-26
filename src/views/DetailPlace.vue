<template>
    <div class="container px-4 px-lg-5">
        <!-- Heading Row-->
        <div class="row gx-4 gx-lg-5 align-items-center my-3">
            <div class="col-lg-7">
<!--                매장사진-->
                <el-carousel indicator-position="outside" trigger="click" >
                    <el-carousel-item v-for="(photo,index) in place.placePhotos" :key="index">
                        <img :src="require('../../public/upload/'+photo)"
                              style="width: 100%; height:100%" class="image"/>

                    </el-carousel-item>
                </el-carousel>

            </div>
<!--            메뉴테이블-->
            <div class="col-lg-5">
                <h1 class="font-weight-light">{{ place.name }}
                <el-tag class="ms-2" type="warning">{{getMenuType(place.menuType)}}</el-tag>
                </h1>
                <p>{{place.content}}</p>

                    <el-table :data="place.menuList" border style="width: 100%" height="250">
                        <el-table-column label="메뉴">
                            <template v-slot="scope">
                                <img :src="require('../../public/upload/'+scope.row.menuImage)" style="width: 100px; height: 100px;" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="menuName" label="메뉴이름" />
                        <el-table-column prop="price" label="가격"  />
                    </el-table>

            </div>

        <!-- 매장 상세정보-->
        <el-descriptions
            title="매장 상세정보"
            direction="vertical"
            :column="4"
            :size="size"
            border
        >
            <el-descriptions-item label="매장명">{{place.name}}</el-descriptions-item>
            <el-descriptions-item label="영업시간">{{ place.startTime }} - {{place.endTime}}</el-descriptions-item>
            <el-descriptions-item label="매장종류" :span="2"> <el-tag size="small">{{place.placeType}}</el-tag></el-descriptions-item>

            <el-descriptions-item label="Address">
                {{place.address}} {{place.detailAddress}}
            </el-descriptions-item>
        </el-descriptions>

        <el-row justify="space-between" >
            <el-col :span="2">
                <el-button size=large  type="success" class="m-3" @click="goList">목록</el-button>
            </el-col>
            <el-col :span="2">
                <el-button size=large :icon="Star" type="warning" class="m-3" @click.stop="addPicplace(place.placeId)">찜</el-button>
            </el-col>
        </el-row>
<!--메뉴 carousel-->
            <div class="row gx-4 gx-lg-5 my-5">
                <div class="col-lg-7">
                    <el-descriptions title="매장 위치"/>
                    <div id="map" style="width:100%;height:350px;"></div>

                </div>
                <div class="col-lg-5">
                    <el-descriptions title="메뉴"/>
                <el-carousel :interval="4000" type="card" height="400px" >
                <el-carousel-item v-for="menu in place.menuList" :key="menu">
<!--                    <template v-slot="scope">-->
                        <img :src="require('../../public/upload/'+menu.menuImage)" style="width: 100%; height: auto;" />
<!--                    </template>-->
                </el-carousel-item>
            </el-carousel>
                </div>

            </div>
     </div>
    </div>
</template>
<script >
import {useRoute, useRouter} from "vue-router";
import {useToast} from "@/common/toast";
import {computed, onMounted, ref} from "vue";
import axios from "@/common/axios";
import {Star} from "@element-plus/icons-vue";
import menuTypes from "@/model/menuType";
import store from "@/store";
export default {
    name: "DetailPlace",
    computed: {
        Star() {
            return Star
        }
    },
    setup() {
        const router = useRouter();
        const {triggerToast} = useToast();
        const route = useRoute();
        const placeId = route.params.placeId;
        const place = ref({});
        onMounted( () => {
            if(window.kakao && window.kakao.maps){
                loadPlace();
            }else {
               loadScript();
            }
        })
        const loadScript = () => {
            const script = document.createElement("script")
            script.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=cecdc2b9460792373bea4e3003d3d9ae";
            script.onload = () => window.kakao.maps.load(loadPlace());
            document.head.appendChild(script) //html>head 안에 스크립트를 추가
        }

        const loadPlace = async () => {
            try {
                const res = await axios.get("api/place/placedetail/" + placeId);
                place.value = res.data.data;
                const mapContainer = document.getElementById("map"),
                    mapOption = {
                        center: new window.kakao.maps.LatLng(place.value.lat, place.value.lng),
                        level: 4
                    }
                const map = new  window.kakao.maps.Map(mapContainer, mapOption);//지도생성
                const markerPosition = new  window.kakao.maps.LatLng(place.value.lat, place.value.lng);
                const marker = new window.kakao.maps.Marker({
                    position: markerPosition
                });
                marker.setMap(map);

            } catch (err) {
                console.log(err);
            }
        }
        const getMenuType = (mtype) => {
            switch (mtype) {
                case menuTypes.KOREAN.text:
                    return menuTypes.KOREAN.label;
                case menuTypes.WESTERN.text:
                    return menuTypes.WESTERN.label;
                case menuTypes.JAPANESE.text:
                    return menuTypes.JAPANESE.label;
                case menuTypes.CHINESE.text:
                    return menuTypes.CHINESE.label;
                default:
                    return menuTypes.ETC.label;
            }
        }
        const currentUser = computed(() => store.getters["getcurrentUser"]);
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
        const goList = () => {
            router.push({name: "List"});
        }


        return {
            place,
            getMenuType,
            addPicplace,
            goList,


        }
    }

}
</script>

<style scoped>
</style>