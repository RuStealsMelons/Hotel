<template>
<div class="home">
  <!--顶部背景图和内容 start-->
  <div class="top_bg">
    <h1>顾客是上帝，服务无极限</h1>
    <p>酒店管理系统·欢迎您，admin</p>
  </div>
  <!--顶部背景图和内容 end-->
  <!--站数据统计 start-->
  <div style="margin-bottom: 15px;color: #144b9f;">
    <div style="width: 12px;height: 12px;background-color: #f9a332;border-radius: 50%;float: left;margin-top: 5px;
margin-right: 8px"></div>站数据统计
  </div>
  <el-row :gutter="40" class="data_row">

    <el-col :span="6">
      <div style="background: linear-gradient(to right,#6d80fe,#23d2fd)">
        <div class="data_left">
          <el-icon><Avatar/></el-icon>
        </div>
        <div class="data_right">
          <h1>{{arriveNums}}<span>人</span></h1>
          <p>今日预达人数</p>
        </div>
      </div>
    </el-col>

    <el-col :span="6">
      <div style="background: linear-gradient(to right,#FF988B,#FF6B88)">
        <div class="data_left">
          <el-icon><Reading/></el-icon>
        </div>
        <div class="data_right">
          <h1>{{leaveNums}}<span>个</span></h1>
          <p>今日预离人数</p>
        </div>
      </div>
    </el-col>

    <el-col :span="6">
      <div style="background: linear-gradient(to right,#09B0E8,#29F49A)">
        <div class="data_left">
          <el-icon><ChatDotSquare/></el-icon>
        </div>
        <div class="data_right">
          <h1>{{orderNums}}<span>笔</span></h1>
          <p>今日订单</p>
        </div>
      </div>
    </el-col>

    <el-col :span="6">
      <div style="background: linear-gradient(to right,#717CFE,#FC83EC)">
        <div class="data_left">
          <el-icon><Clock/></el-icon>
        </div>
        <div class="data_right">
          <h1>{{tradeNums}}<span>元</span></h1>
          <p>今日营业额</p>
        </div>
      </div>
    </el-col>


  </el-row>
  <!--站数据统计 end-->

  <!--近七天入住人数 start-->
  <p style="margin-bottom: 15px;color: #144b9f;">
    <div style="width: 12px;height: 12px;background-color: #f9a332;border-radius: 50%;float: left;margin-top: 5px;
margin-right: 8px"></div>近七天入住人数
  </p>
  <el-row>
    <el-col :span="24">
      <SevenDaysOccupancy :legendData="legendData" :series-data="seriesData" :category-data="categoryData"
                          height="400px" width="100%" id="bar"/>
    </el-col>
  </el-row>
  <!--近七天入住人数 end-->

</div>
</template>

<script setup lang="ts">
import { ref,onMounted } from 'vue'
import {getIndexTotalApi} from "@/api/hotel/home/home";
import SevenDaysOccupancy from "./components/SevenDaysOccupancy.vue";
// 统计今日预计到达人数
const arriveNums = ref(0)
// 统计今日预离人数
const leaveNums = ref(0)
// 统计今日订单数量
const orderNums = ref(0)
// 统计今日营业额
const tradeNums = ref(0)

// 学科成绩对比
const legendData = ref(['入住人数'])
const seriesData = ref([])
const categoryData = ref([])

// 统计数据
const getIndexTotal = async ()=>{
  const { data } = await getIndexTotalApi()
  if(data.status === 200){
    arriveNums.value = data.result.arriveNums
    leaveNums.value = data.result.leaveNums
    orderNums.value = data.result.orderNums
    tradeNums.value = data.result.tradeNums

    categoryData.value = data.result.checkinNum.categoryList
    seriesData.value = data.result.checkinNum.barEchartsSeriesList
  }
}



onMounted(()=> {
  getIndexTotal()
})
</script>

<style scoped>
.home {
  width: 100%;
}
.top_bg{
  width: 100%;
  height: 200px;
  background-image: url(@/assets/hotel/images/banner5.jpg);
  background-size: cover;
  background-repeat: no-repeat;
  color: white;
  line-height: 60px;
  text-align: center;
  margin: 0 auto 10px;
}
.top_bg h1 {
  font-size: 60px;
  text-shadow: 3px 3px 0px #515151;
  padding-top: 50px;
}
.top_bg p{
  font-weight: lighter;
  font-size: 18px;
}

.data_row .el-col {
  height: 100px;
  margin-bottom: 20px;
  overflow: hidden;
}
.data_row .el-col>div {
  width: 100%;
  height: 100%;
  border-radius: 10px;
  color: white;
}

.data_left {
  float: left;
  width: 40%;
  height: 100%;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}
.data_left .el-icon {
  font-size: 60px;
}
.data_right {
width: 60%;
  float: right;
  margin-top: 10px;
}
.data_right h1 {
  font-size: 40px;
}
.data_right h1 span {
  font-size: 15px;
  margin-left: 10px;
}
.data_right p {
  font-size: 14px;
  font-weight: 600;
  margin-left: 3px;
}
</style>
