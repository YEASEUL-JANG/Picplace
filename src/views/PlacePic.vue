<template>
    <section class="border-bottom" id="features">
        <div  class="container  my-3">
            <div class="row my-2">
                <div class="col-lg-6">
                <PlaceMap @onSearch="picplace"
                          :height="mapsize"/>
                </div>

            <div class="col-lg-6">
            <!-- place 목록-->
            <el-row  gutter=16>
                <el-col
                        v-for="(place,index) in places"
                        :key="index"
                        :span="8"
                        class="el-col"
                >
                    <el-card :body-style="{ padding: '0px', cursor:'pointer' }"
                             shadow="hover"
                             @click="placeDetail(place.placeId)">
                        <img :src="'./upload/'+place.placePhotos[0]"
                             class="image" style="width:100%;height:200px;"
                        />
                        <div style="padding: 10px">
                            <span>{{ place.name }}</span>
                            <el-tag class="ms-2" type="warning">{{getMenuType(place.menuType)}}</el-tag>
                            <div class="bottom">
                                <time class="time">{{ place.address }}</time>
                                <el-button :icon="Star" type="warning"  @click.stop="deletePic(place.placeId)" class="ms-1">삭제</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            </div>
            </div>
        </div>
        <div  v-if="places.length == 0" class="container  my-3">
            찜한 장소가 없습니다.
        </div>
    </section>
</template>

<script>
import {computed, ref} from "vue";
import { Star} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";
import axios from "@/common/axios";
import menuTypes from "@/model/menuType";
import PlaceMap from "@/components/PlaceMap.vue";
import store from "@/store";
import {useToast} from "@/common/toast";

export default {
    name: "PlacePic",
    components: { PlaceMap },
    computed: {
        Star() {
            return Star
        },
    },
    setup(){
        const router = useRouter();
        const {triggerToast} = useToast();
        const loading = ref('false');
        const places = ref([]);
        const positions = ref([]);
        const map = ref(null);
        const mapsize = ref(800);
        const currentUser = computed(() => store.getters["getcurrentUser"]);
        const picplace = async () => {
            console.log("##userInfo",currentUser)
            loading.value = true;
            positions.value = []
            try { //찜목록 조회
                const res = await axios.post('api/place/placePicList/'+currentUser.value.userId);
                console.log("##PicPlaceList",res.data)
                places.value = res.data.data
                loading.value = false;
                readyMap();
            }catch (err){
                console.log(err);
            }
        }

        const readyMap = () => {
            if (places.value.length === 0) {
                return;
            }
            //지도 준비
            const mapContainer = document.getElementById("map"),
                mapOption = {
                    center: new window.kakao.maps.LatLng(places.value[0].lat, places.value[0].lng),
                    level: 4
                }
            console.log("##mapOption: ", mapOption)
            map.value = new window.kakao.maps.Map(mapContainer, mapOption);
            for (const place of places.value) {
                positions.value.push({
                    content: '<div>' + place.name + '</div>',
                    latlng: new window.kakao.maps.LatLng(place.lat, place.lng)
                })
            }
            console.log("##positions: ", positions)
            for (let i = 0; i < positions.value.length; i++) {
                let marker = new window.kakao.maps.Marker({
                    map: map.value,
                    position: positions.value[i].latlng
                });
                let infowindow = new window.kakao.maps.InfoWindow({
                    content: positions.value[i].content
                })
                window.kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map.value, marker, infowindow));
                window.kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
            }
        }

        const makeOverListener = (map,marker,infowindow)=> {
            return function (){
                infowindow.open(map, marker);
            }
        }
        const makeOutListener = (infowindow)=> {
            return function () {
                infowindow.close();
            }
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
        const placeDetail =(placeId) => {
            router.push("/detailPlace/"+placeId);
        }
        const deletePic = async (placeId) => {
            const userId = {
                userId: currentUser.value.userId
            }
            const res = await axios.post("api/place/placePic/delete/"+placeId,userId)
            console.log("deletePic: ",res.data)
            if(res.data.code == 200){
                triggerToast("삭제되었습니다.")
                picplace();

            }else{
                triggerToast("Unknown Error","danger")
            }

        }

        return{
            picplace,
            places,
            placeDetail,
            loading,
            getMenuType,
            mapsize,
            deletePic
        }
    }
}
</script>

<style scoped>

.el-col{
    margin-bottom: 16px;
}

.time {
    font-size: 12px;
    color: #999;
}

.bottom {
    margin-top: 13px;
    line-height: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.button {
    padding: 0;
    min-height: auto;
}
.searchBox{
    width: 100%;
}
.image {
    width: 100%;
    display: block;
}
</style>
