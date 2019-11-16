Vue.component('app-towns', {
    template: ` <div v-for="town in towns">
                    <b-card
                      title={{this.town.name}}
                      img-src="https://picsum.photos/600/300/?image=25"
                      img-alt="Image"
                      img-top
                      tag="article"
                      style="max-width: 20rem;"
                      class="display-1">

                      <b-button @click="goToTownPage(town.id)" href="#" variant="primary">{{town.id}}</b-button>
                    </b-card>
                </div>`,
    data: () => ({
        towns: []
    }),
    mounted: function() {
        this.loadTowns();
        this.goToTownPage();
    },
    methods: {
        loadTowns : function() {
        
        var names = ["Lyon", "Saint-Etienne", "Paris", "Toulouse"];
        
        /*for(var i=0; i<4; i++){
            var town.id = i;
            var town.name = names[i];
            towns.push(town);
        }*/
        
            //axios.get('/api/towns/search/getStationsByIdTown')
            //    .then ( (ok) => {this.towns = ok.data._embedded.towns;})
            //    .catch( (err) => {alert(err)})
        },
        goToTownPage: function(idTown) {
            window.location.href='/town/' + idTown;
            localStorage.setItem( 'idTown', idTown );
        }
    }
});

let vm = new Vue({
    el: '#towns-template',
    template: '<app-towns></app-towns>'
});