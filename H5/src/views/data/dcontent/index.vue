<template>
  <div class="content-cont">
    <div class="top-box">
      <div class="left">
        <dnumber :dheight="110" title="总事务量" size="2rem" :dnumber="numberData.total" :icon="'kucunguanli'" :color="'#ffff43'">
        </dnumber>
      </div>
      <div class="right">
        <div class="content">
          <dnumber :dheight="110" title="正常事务量" size="2rem" :dnumber="numberData.normal" :icon="'success'" :color="'#25f3e6'">
          </dnumber>
          <dnumber :dheight="110" title="异常事务量" size="2rem" :dnumber="numberData.warning" :icon="'warning'" :color="'#f84a4a'">
          </dnumber>
        </div>
      </div>
    </div>
    <div class="content-box">
      <databox title="近一个月事务趋势图" :dheight="380" :icon="'account'" :boxb="false">
        <div class="content-wapper">
          <!-- <ve-line :data="chartData" :extend="extend"></ve-line> -->
          <ve-bar :data="chartData" :extend="extend" :height="'350px'"></ve-bar>
        </div>
      </databox>
      <databox title="服务排名" :dheight="220" :icon="'account'">
        <myevent :data="myevent"></myevent>
      </databox>
    </div>
  </div>
</template>

<script>
import dnumber from './dnumber'
import myevent from './myevent'
import { LINE_DATA } from '../test/data'
export default {
  components: {
    dnumber,
    myevent,
  },
  props: {
    numberData: Object,
    username: String,
  },
  data() {
    this.extend = {
      series: {
        label: {
          normal: {
            show: true,
          },
        },
      },
      legend: {
        textStyle: { color: '#fff' },
        right: '6%',
      },
      yAxis: {
        axisLabel: {
          textStyle: {
            color: '#fff',
          },
        },
      },
      xAxis: {
        axisLabel: {
          textStyle: {
            color: '#fff',
          },
        },
      },
    }

    return {
      chartData: LINE_DATA,
      myevent: [],
      noMyevent: false,
      repoData: {
        columns: ['reposName', 'size', 'forks'],
        rows: [],
      },
      noRepoSize: false,
    }
  },
  methods: {
    getData(username) {
      let comUrl = '/api/users/'
      let url1 = comUrl + username + '/events'
      let url2 = comUrl + username + '/repos'
      this.$axios
        .all([this.$axios.get(url1), this.$axios.get(url2)])
        .then(
          this.$axios.spread((res1, res2) => {
            //我最近操作
            let data1 = JSON.parse(JSON.stringify(res1.data))
            if (data1.length < 1) {
              this.noMyevent = true
            } else {
              this.myevent = data1
              //console.log(this.myevent)
            }

            //每个仓库的大小
            let data2 = JSON.parse(JSON.stringify(res2.data))
            if (data2.length < 1) {
              this.noRepoSize = true
            } else {
              let dataR = []
              for (var i = 0; i < data2.length; i++) {
                let reposName = data2[i].name
                let size = data2[i].size
                let forks = data2[i].forks
                let objR = {
                  reposName: reposName,
                  size: size,
                  forks: forks,
                }
                dataR.push(objR)
              }
              //console.log(dataR);
              this.repoData.rows = dataR
              //console.log(this.repoData.rows)
            }

            return
          })
        )
        .catch(() => {
          // console.log(err.message)
        })
    },
  },
  watch: {
    username(username) {
      if (username) {
        this.getData(username)
      }
    },
  },
}
</script>

<style lang="scss">
.content-cont {
  width: 100%;
  height: 100%;
  .top-box {
    display: flex;
    width: 100%;
    height: 110px;
    overflow: hidden;
    .left {
      width: 1/3 * 100%;
      background: rgba(35, 72, 135, 0.4);
    }
    .right {
      flex: 1;
      padding-left: 15px;
      .content {
        width: 100%;
        height: 100%;
        background: rgba(35, 72, 135, 0.4);
        .number-box {
          width: 50%;
          float: left;
        }
      }
    }
  }
}
</style>
