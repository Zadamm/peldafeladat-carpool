<template>
  <div class="modifyRoute">

      <form v-if="dataloaded == 0">
        <div>
          <label>Név:</label>
          <input v-model="route.name" type="text">
        </div>

        <div>
          <label>Szabad helyek száma:</label>
          <input v-model="route.emptyPlaces" type="text">
        </div>

        <div>
          <h3>Útvonal</h3>

          <h4>Indulás:</h4>
          <div>
            <label>Hely:</label>
            <input v-model="route.meetingPoints[0].place" type="text">
          </div>
          <div>
            <label>Idő:</label>
            <input v-model="route.meetingPoints[0].time" type="text">
          </div>
          <h4>Megállók:</h4>
          <input type="button" v-on:click="addMeetingPoint()" value="Megálló hozzádása"/>
          <div v-if="route.meetingPoints.length > 2" v-for="(meetingPoint,index) in meetingPointsWithoutStartEnd" v-bind:key="index">
            <div>
            <label>Hely:</label>
              <input v-model="meetingPoint.place" type="text">
            </div>
            <div>
              <label>Idő:</label>
              <input v-model="meetingPoint.time" type="text">
            </div>
            <input type="button" v-on:click="removeMeetingPoint(meetingPoint.id)" value="Megálló torlese"/>
          </div>

          <h4>Érkezés:</h4>
          <div>
            <label>Hely:</label>
            <input v-model="route.meetingPoints[1].place" type="text">
          </div>
          <div>
            <label>Idő:</label>
            <input v-model="route.meetingPoints[1].time" type="text">
          </div>

          <button v-on:click="addRoute($event)">Útvonal hozzádása</button>
        </div>
      </form>

  </div>
</template>

<script>
/* eslint-disable */
//import axios from 'axios'
import {AXIOS} from './http-common'
import moment from 'moment'

export default {
  name: 'ModifyRoute',

  data () {
    return {
      carId: 0,
      response: [],
      errors: [],
      route: {
        name: "Jani",
        emptyPlaces: 3,
        meetingPoints: []
      },
      meetingPointsWithoutStartEnd: [],
      dataloaded: 1
    }
  },
  methods: {
    removeMeetingPoint: function (mpID) {
      console.log("removeMP")
      var mpL = this.route.meetingPoints
      for(var i in mpL){
        if(mpL[i].id == mpID){
          mpL.splice(i,1)
        }
      }
      this.meetingPointsWithoutStartEnd = this.route.meetingPoints.slice(2)
      var self = this
      AXIOS.get('/meetingpoints/' + mpID + '/remove')
        .then(response => {
          console.log("removedMP: " + mpID)
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    addMeetingPoint: function () {
      console.log("addMP")      
      this.route.meetingPoints.push(
        {
          place: "Valahol",
          time: "2018-06-01 10:00",
        }        
      )
      this.meetingPointsWithoutStartEnd = this.route.meetingPoints.slice(2)
    },
    getCar () { 
      var self = this
      this.dataloaded++
      AXIOS.get(`/cars/` + self.carId)
        .then(response => {
          self.route = response.data
          console.log(response.data)
          self.getMeetingPoints()
          self.dataloaded--
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    getMeetingPoints () {
      console.log("getMettingPOints meghivodik")
      var self = this
      this.dataloaded++
      AXIOS.get(`/cars/` + self.carId + '/meetingpoints')
        .then(response => {
          self.route.meetingPoints = response.data
          console.log(response.data)
          for (var j in self.route.meetingPoints) {
            self.route.meetingPoints[j].time = moment(self.route.meetingPoints[j].time).format('YYYY-MM-DD HH:mm')
          }
          this.meetingPointsWithoutStartEnd = this.route.meetingPoints.slice(2)
          self.dataloaded--
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    addRoute: function(event){
      if (event) event.preventDefault();

      for(var i in this.route.meetingPoints){
        this.route.meetingPoints[i].time = moment(this.route.meetingPoints[i].time).format()
      }
      var self = this;
      AXIOS.post('/routes' + '?name=' + self.route.name + '&emptyPlaces=' + self.route.emptyPlaces + '&carID=' + self.carId,  self.route.meetingPoints)
        .then(response => {
          // JSON responses are automatically parsed.
          console.log(response)
          console.log(response.data)
        })
        .catch(e => {
          this.errors.push(e)
        })

    }
  },
  created: function () {      
    this.carId = this.$route.params.carId
    this.getCar()
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

  div {
    padding: 10px;
  }
</style>
