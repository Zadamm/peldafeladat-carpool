<template>
  <div class="front">
    <div>
        <label>Név:</label>
        <input v-model="name" type="text" value="Hanna">
    </div>

    <div>
        <label>Csatlakozási helyek:</label>
        <select v-model="placeID">
            <option v-for="(p,index) in placelist" v-bind:key="index" v-bind:value="p.id">{{p.place}} időpont: {{p.time}}</option>
        </select>
        <button v-on:click="addPassengerToMeetingPoint">Csatlakozás</button>
    </div>

  </div>
</template>

<script>

import {AXIOS} from './http-common'

export default {
  name: 'JoinCar',
  data () {
    return {
      carId: 0,
      placelist: [],
      name: '',
      placeID: '',
      response: [],
      errors: []
    }
  },
  methods: {
    getMeetingPoints () {
      var self = this
      AXIOS.get(`/cars/` + self.carId + '/meetingpoints')
        .then(response => {
          self.placelist = response.data
          console.log(response.data)
        })
        .catch(e => {
          this.errors.push(e)
        })
    },

    addPassengerToMeetingPoint () {
      var self = this
      var passenger = {
        name: this.name
      }
      console.log('elkuldott uzenet' + 'mpID: ' + this.placeID + ' name: ' + passenger.name)
      AXIOS.post('/addpassengers' + '?mpID=' + self.placeID, passenger)
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
    console.log('created meghívódik')
    this.carId = this.$route.params.carId
    this.getMeetingPoints()
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
</style>
