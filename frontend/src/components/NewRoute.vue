<template>
  <div class="newRoute">

      <form>
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

          <button v-on:click="addMeetingPoint($event)">Megálló hozzádása</button>
          <div v-if="route.meetingPoints.length > 2" v-for="(meetingPoint,index) in meetingPointsWithoutStartEnd" v-bind:key="index">
            <div>
            <label>Hely:</label>
              <input v-model="meetingPoint.place" type="text">
            </div>
            <div>
              <label>Idő:</label>
              <input v-model="meetingPoint.time" type="text">
            </div>
          </div>

          <h4>Érkezés:</h4>
          <div>
            <label>Hely:</label>
            <input v-model="route.meetingPoints[1].place" type="text" value="Pont">
          </div>
          <div>
            <label>Idő:</label>
            <input v-model="route.meetingPoints[1].time" type="text" value="2018-06-01 08:00:00.000">
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
  name: 'NewRoute',

  data () {
    return {
      response: [],
      errors: [],
      route: {
        name: "Jani",
        emptyPlaces: 3,
        meetingPoints: []
      }
    }
  },
  created: function () {
    this.route.meetingPoints.push(
      {
        place: "Moszkva",
        time: "2018-06-01 07:00",
      },
      {
        place: "Pont",
        time: "2018-06-01 08:00",
      }

    )
  },
  methods: {
    addMeetingPoint: function(event){
      if (event) event.preventDefault();
      this.route.meetingPoints.push(
        {
          place: "Valahol",
          time: "2018-06-01 10:00",
        }
      );
    },
    addRoute: function(event){
      if (event) event.preventDefault();

      for(var i in this.route.meetingPoints){
        this.route.meetingPoints[i].time = moment(this.route.meetingPoints[i].time, "yyyy-MM-ddTHH:mm:ss.SSSZ").format()
      }
      var self = this;
      AXIOS.post('/routes' + '?name=' + self.route.name + '&emptyPlaces=' + self.route.emptyPlaces,  self.route.meetingPoints)
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
  computed: {
    meetingPointsWithoutStartEnd: function () {
      return this.route.meetingPoints.slice(2);
    }
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
