<template>
  <div class="login">

<input type="text" v-model="token" />
<p v-on:click="sendAuth()">Bejelentkezes"</p>

    <form>
        <div>
            <label>Felhasznalonev:
            <input type="text" v-model="username" value="password"/></label>
        </div>
        <div>
            <label>Jelszo:
            <input type="text" v-model="password" value="password"/></label>
        </div>
        <input type="button" v-on:click="sendAuth()" value="Bejelentkezes"/>
        <div v-if="$route.params.error">
            Invalid username and password.
        </div>
        <div v-if="$route.params.logout">
            You have been logged out.
        </div>
    </form>
  </div>
</template>

<script>

import AXIOS from 'axios'

export default {
  name: 'Login',
  data () {
    return {
      username: '',
      password: '',
      response: [],
      errors: [],
      token: null
    }
  },
  created: function (){
    this.sendAuth()
  },
  methods: {
    sendAuth () {
      var self = this
      console.log('kuldott adatok:')
      console.log(this.username + ' : ' + this.password)
      
      var bodyFormData = new FormData()
      bodyFormData.set('userName', 'user')
      bodyFormData.set('password', 'password')
      /*
      var bodyFormData = {
        userName: 'user',
        password: 'password'
      }
      */
      AXIOS({
        method: 'get',
        url: /*'/login'*/ 'http://localhost:8080/users/me',
        /*data: bodyFormData,*/
        config: {headers: { 'Authorization': 'Bearer ' + self.token }}
        
        
      }
      /* '/login?userName=' + self.username + '&password=' + self.password
      /*
      ,{
        userName: self.username,
        password: self.password
      }
      */)
        .then(response => {
          console.log('bejelentkezes elfogadva, response: ')
          console.log(response.body)
          console.log('header: ' + response.headers)
          /*
          if (response.status === 200) {
            self.$router.push('/FrontPage')
          }
          */
        })
        .catch(e => {
          this.errors.push(e)
        })
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
table td {
    border:1px solid #000000;
 }
</style>
