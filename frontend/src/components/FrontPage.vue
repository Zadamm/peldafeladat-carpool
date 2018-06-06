<template>
  <div class="front">
    <router-link :to="{ name: 'NewRoute'}" tag="button">Új útvonal</router-link>
    <table>
      <th>Név</th>
      <th>Útvonal</th>
      <th>Üres helyek</th>
      <th>Csatlakozás</th>
      
      <tr v-if="dataloaded"
          v-for="(car,index) in carList"
          v-bind:key="index"
          v-bind:item="car">
        <td>{{car.name}}</td>
        <td>
          <div v-for="(meetingpoint,index) in car.meetingPoints"
               v-bind:key="index">
            <p>Hely: {{meetingpoint.place}} Ido: {{meetingpoint.time}}</p>
            <div v-if="typeof meetingpoint.passengers !== 'undefined' && meetingpoint.passengers.length > 0" >Utasok:
              <span v-for="(passenger,index) in meetingpoint.passengers"
                    v-bind:key="index">{{passenger.name}} </span>
            </div>
          </div>
        </td>
        <td>{{car.emptyPlaces}}</td>
        <td>
        <router-link :to="{ name: 'JoinCar', params:{'carId': car.id}}" tag="button">Csatlakozás</router-link>
        </td>
      </tr>
    </table>

  </div>
</template>

<script>

import {AXIOS} from './http-common'

export default {
  name: 'FrontPage',
  data () {
    return {
      carList: [],
      response: [],
      errors: [],
      dataloaded: false
    }
  },
  methods: {
    getAllCar () {
      var self = this
      console.log('getAllCar meghívódik')
      AXIOS.get(`/cars`)
        .then(response => {
          self.carList = response.data
          console.log('/cars response data: ')
          console.log(response.data)
          console.log('carListba irt adat: ')
          console.log(self.carList)
          for (var i in self.carList) {
            self.getACarMeetingPointList(self.carList[i].id)
          }
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    getACarMeetingPointList (carID) {
      var self = this
      console.log('getACarMeetingPointList meghívódik')
      AXIOS.get(`/cars/` + carID + '/meetingpoints')
        .then(response => {
          console.log('meetingpoints response data: ')
          console.log(response.data)

          for (var i in self.carList) {
            if (self.carList[i].id === carID) {
              var car = self.carList[i]
              car.meetingPoints = response.data
              console.log('carListba irt meetingpoint adat: ')
              console.log(car.meetingPoints)
              for (var j in car.meetingPoints) {
                self.getPassenegersByMeetingPoint(car.meetingPoints[j].id, carID)
              }
            }
          }
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    getPassenegersByMeetingPoint (mpID, carID) {
      var self = this
      console.log('getPassenegersByMeetingPoint meghívódik')
      AXIOS.get('/meetingpoints/' + mpID + '/passengers')
        .then(response => {
          console.log('passengers response data: ')
          console.log(response.data)
          for (var i in self.carList) {
            if (self.carList[i].id === carID) {
              var car = self.carList[i]
              for (var j in car.meetingPoints) {
                var mp = car.meetingPoints[j]
                if (mp.id === mpID) {
                  mp.passengers = response.data
                  console.log('carList meetingpointjaba irt passenger adat: ')
                  console.log(mp.passengers)
                }
              }
            }
          }

          // making sure all the data is loaded before sending to the HTML elements

          // length of the lists with the data
          var carListLength = self.carList.length - 1
          var lastMeetingPointPlace = self.carList[carListLength].meetingPoints.length - 1

          // last car's last meetingpoint
          var lastmpID = self.carList[carListLength].meetingPoints[lastMeetingPointPlace].id

          // check if the current meetingpointID is the same as the last meetingpointID of the last car
          if (mpID === lastmpID) {
            self.dataloaded = true
          }
        })
        .catch(e => {
          this.errors.push(e)
        })
    }
  },
  created: function () {
    console.log('created meghívódik')
    this.getAllCar()
    console.log('CarList keszen: ')
    console.log(this.carList)
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
