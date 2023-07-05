<template>
  <!-- Header-->
    <header class="bg-light">
        <div class="container my-3">
            <!-- 검색창 -->
            <SearchForm @searchPlace="searchPlace"/>

        </div>
    </header>
    <section class="border-bottom" id="features">
        <div class="container  my-3">
<!--            스켈레톤-->
            <el-row gutter="16" v-if="loading">
                <el-col
                    v-for="i in 9"
                    :key="i"
                    :span="8"
                    class="el-col"

                >
            <el-skeleton style="width: 100%"
             animated>
                <template #template>
                    <el-skeleton-item variant="image" style="width: 100%; height: 300px" />
                    <div style="padding: 14px">
                        <el-skeleton-item variant="p" style="width: 50%" />
                        <div style="display: flex;
            align-items: center;
            justify-items: space-between;">
                            <el-skeleton-item variant="text"  />
                            <el-skeleton-item variant="text" style="width: 30%" />
                        </div>
                    </div>
                </template>
            </el-skeleton>
                </el-col>
            </el-row>
            <div class="my-2">
                <PlaceMap @onSearch="searchPlace"
                          :height="mapsize"
                />
            </div>
<!--        place 목록-->
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
                             class="image" style="width:100%;height:350px;"
                        />
                        <div style="padding: 10px">
                            <span>{{ place.name }}</span>
                            <el-tag class="ms-2" type="warning">{{getMenuType(place.menuType)}}</el-tag>
                            <div class="bottom">
                                <time class="time">{{ place.address }}</time>
                                <el-button :icon="Star" type="warning"  @click.stop="addPicplace(place.placeId)" class="ms-1">찜</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
        <div style="display: flex; justify-content: center; width: 100%;" class="my-5" >
            <el-pagination background layout="prev, pager, next"  @current-change="handlePageChange" :total="50" />
        </div>
    </section>
</template>

<script>
import {computed, ref} from "vue";
import { Star} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";
import axios from "@/common/axios";
import SearchForm from "@/components/SearchForm.vue";
import menuTypes from "@/model/menuType";
import PlaceMap from "@/components/PlaceMap.vue";
import {useToast} from "@/common/toast";
import store from "@/store";

export default {
    name: "PlaceList",
    components: { PlaceMap, SearchForm},
    computed: {
        Star() {
            return Star
        },
    },
    setup(){
        const router = useRouter();
        const currentUser = computed(() => store.getters["getcurrentUser"]);
        const {triggerToast} = useToast();
        const loading = ref('false');
        const places = ref([]);
        const positions = ref([]);
        const mapsize = ref(350);
        const map = ref(null);
        const searchthing = ref({
            placeName: '',
            menuKeyword: '',
            address: '',
            placeType: '',
            menuType:'',
            pageNum: 1
        })
        const searchPlace = async (placeSearch) => {
            if (placeSearch)
                searchthing.value = placeSearch
            loading.value = true;
            positions.value = []
            try {
                const res = await axios.post('api/place/placelist', searchthing.value);
                console.log("##placeList",res.data)
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
            const mapContainer = document.getElementById("map")

               const mapOption = {
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
        const handlePageChange = (number) => {
            console.log(number)
            searchthing.value.pageNum = number;
            searchPlace();
        }
        return{
            searchPlace,
            places,
            placeDetail,
            loading,
            getMenuType,
            positions,
            mapsize,
            addPicplace,
            handlePageChange
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

.image {
    width: 100%;
    display: block;
}
</style>
