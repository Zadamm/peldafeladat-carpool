<template>
  <div class="front">
    <table>
      <th>Név</th>
      <th>Talakozasok</th>
      <tr v-if="dataloaded == 0"
          v-for="(pass,index) in passengerList"
          v-bind:key="index"
          v-bind:item="pass">
        <td>{{pass.name}}</td>
        <td>
          <div v-for="(meetingpoint,index) in pass.meetingPoints"
               v-bind:key="index">
            <p>Hely: {{meetingpoint.place}} Ido: {{meetingpoint.time}}</p>
            <button v-on:click="deleteMeetingPoint(meetingpoint.id, pass.id)">Torol</button>
          </div>
        </td>
      </tr>
    </table>

  </div>
</template>

<script>

import {AXIOS} from './http-common'
import moment from 'moment'

export default {
  name: 'PassengerList',
  data () {
    return {
      passengerList: [],
      response: [],
      errors: [],
      dataloaded: 1 // tracking the requests of the data, when it reaches 0 all the requests responded
    }
  },
  methods: {
    getAllPassenger () {
      var self = this
      this.dataloaded++
      console.log('getAllPassenger meghivodik')
      AXIOS.get(`/passengers`)
        .then(response => {
          self.dataloaded--
          self.passengerList = response.data
          console.log('/passengers response data: ')
          console.log(response.data)
          console.log('passengerListba irt adat: ')
          console.log(self.passengerList)
          for (var i in self.passengerList) {
            self.getMeetingpointsOfPassenger(self.passengerList[i].id)
          }
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    getMeetingpointsOfPassenger (pid) {
      var self = this
      this.dataloaded++
      console.log('getMeetingpointsOfPassenger meghivodik')
      AXIOS.get(`/passengers/` + pid + '/meetingpoints')
        .then(response => {
          self.dataloaded--
          for (var i in self.passengerList) {
            if (self.passengerList[i].id === pid) {
              self.passengerList[i].meetingPoints = response.data
              console.log('passengers meetingpointba irt adat: ')
              console.log(self.passengerList[i].meetingPoints)
              for(var j in self.passengerList[i].meetingPoints){
                self.passengerList[i].meetingPoints[j].time = moment(self.passengerList[i].meetingPoints[j].time).format('YY-MM-DD HH:mm')
              }
            }
          }
          console.log('/passengers/pid/meetingpoints response data: ')
          console.log(response.data)
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    deleteMeetingPoint (mid, pid) {
      var self = this
      console.log('deleteMeetingPoint meghivodik')
      AXIOS.get(`/passengers/` + pid + '/meetingpoints/' + mid + '/remove')
        .then(response => {
          console.log('passengers meetingpoint torlodott') 
        })
        .catch(e => {
          this.errors.push(e)
        })
      this.getAllPassenger
    }
  },
  created: function () {
    console.log('created meghívódik')
    this.getAllPassenger()
    this.dataloaded--
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
table td {
    border:1px solid #000000;
 }
</style>
