import axios from "@/common/axios";
import {computed} from "vue";
import store from "@/store";
import {useToast} from "@/common/toast";
const {triggerToast} = useToast();

const currentUser = computed(() => store.getters["getcurrentUser"]);
export const picplace = async (placeId) => {
    try {
        const userid = currentUser.value.userId;
        console.log("유저아이디 : " + userid + " place아이디 : " + placeId);
        const res = await axios.post("api/place/placePic/" + placeId, {
            userId: userid
        });
        if (res.data > -1) {
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