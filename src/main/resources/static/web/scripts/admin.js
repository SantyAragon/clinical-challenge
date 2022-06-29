Vue.createApp({
    data() {
      return {
        pacients: [],
        pacientes: [],
        profesionales: [],

        nombrePro: "",
        apellidoPro: "",
        especialidad: "",
        // servicio: 0,
        emailPro: "",
        contraseñaPro: "",
        
      }
    },

    mounted(){
      axios.get('/api/pacientes')
        .then (data =>{
          this.pacientes = data.data;
          this.pacientes = this.pacientes.sort((a, b) => a.apellido - b.apellido);
          // this.console.log(this.pacientes)
        })
        .catch(error => console.warn(error.message));
      axios.get('/api/profesionales')
        .then (data =>{
          this.profesionales = data.data;
          this.profesionales = this.profesionales.sort(function(a, b) {
            return a.apellido - b.apellido;
          });
          console.log(this.profesionales)
        })
        .catch(error => console.warn(error.message));

      
    },
    
    created() {
      // sort((a,b)=>a-b)
      

    },
  
    methods: {
      nuevoProfesional() {
        axios.post("/api/profesional", `nombre=${this.nombrePro}&apellido=${this.apellidoPro}&especialidad=${this.especialidad}}&email=${this.emailPro}&contraseña=${this.contraseñaPro}`)
            .then(response => {
              window.location.reload()
                window.alert("Profesional agregado")
          })
          .catch(error => {
              console.log(error)
              window.alert("No se pudo agregar")
          });
      },



      

    },

    computed: {
  
    }
  
  }).mount('#app2')
  

  window.addEventListener('DOMContentLoaded', event => {
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

});

const targets = document.querySelectorAll('[data-target]')
const content = document.querySelectorAll('[data-content]')
      targets.forEach(target => {
        target.addEventListener('click', ()=> {
          content.forEach(c => {
            c.classList.remove('active')
          })
          const t = document.querySelector(target.dataset.target)
          t.classList.add('active')
        })
      })



