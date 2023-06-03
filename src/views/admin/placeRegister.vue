<template>
    <el-form :model="placeform" label-width="120px">
        <el-form-item label="Name">
            <el-input v-model="placeform.name" />
        </el-form-item>
        <el-form-item label="Address">
            <el-input v-model="placeform.address" id="address" @click="execDaumPostcode"/>
            <el-input v-model="placeform.detailAddress" id="detailaddress"/>
<!--            <el-select v-model="placeform.region" placeholder="please select your zone">-->
<!--                <el-option label="Zone one" value="shanghai" />-->
<!--                <el-option label="Zone two" value="beijing" />-->
<!--            </el-select>-->
        </el-form-item>
        <el-form-item label="Activity time">
            <el-col :span="11">
                <el-date-picker
                        v-model="placeform.date1"
                        type="date"
                        placeholder="Pick a date"
                        style="width: 100%"
                />
            </el-col>
            <el-col :span="2" class="text-center">
                <span class="text-gray-500">-</span>
            </el-col>
            <el-col :span="11">
                <el-time-picker
                        v-model="placeform.date2"
                        placeholder="Pick a time"
                        style="width: 100%"
                />
            </el-col>
        </el-form-item>
        <el-form-item label="Instant delivery">
            <el-switch v-model="placeform.delivery" />
        </el-form-item>
        <el-form-item label="PlaceType">
            <el-radio-group v-model="placeform.placeType">
                <el-radio label="카페" name="PlaceType" />
                <el-radio label="Promotion activities" name="PlaceType" />
            </el-radio-group>
        </el-form-item>
        <el-form-item label="Menutype">
            <el-radio-group v-model="placeform.type">
                <el-radio label="Online activities" name="Menutype" />
                <el-radio label="Promotion activities" name="Menutype" />
                <el-radio label="Offline activities" name="Menutype" />
                <el-radio label="Simple brand exposure" name="Menutype" />
            </el-radio-group>
        </el-form-item>
        <el-form-item label="Resources">
            <el-radio-group v-model="placeform.resource">
                <el-radio label="Sponsor" />
                <el-radio label="Venue" />
            </el-radio-group>
        </el-form-item>
        <el-form-item label="Activity form">
            <el-input v-model="placeform.desc" type="textarea" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">Create</el-button>
            <el-button>Cancel</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import {ref} from 'vue'
import {useToast} from "@/common/toast";
export  default {
    setup() {
        const {triggerToast} = useToast();
        const placeform = ref({
            name : '',
            startTime : '',
            endTime: '',
            content: '',
            placeType: '',
            menuType: '',
            address: '',
            detailAddress: '',
            zipcode: ''
        })
        const execDaumPostcode = () => {
            new window.daum.Postcode({
                oncomplete: function(data) {
                    var addr = ''; // 주소 변수
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }
                    placeform.value.zipcode = data.zonecode;
                    placeform.value.address = addr;
                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById("detailaddress").focus();
                }
            }).open();
        }
        const onSubmit = () => {
            triggerToast('등록완료')
        }
        return {
            placeform,
            onSubmit,
            execDaumPostcode,

        }
    }
}

</script>


<style scoped>

</style>