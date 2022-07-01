// let principal = document.getElementById("layoutSidenav_content")

Vue.createApp({
  data() {
    return {
      pacients: [],
      pacientes: [],
      profesionales: [],
      servicios: [],
      productos: [],

      nombrePro: "",
      apellidoPro: "",
      especialidadPro: "",
      servicioPro: 0,
      emailPro: "",
      contraseñaPro: "",
      emailDoc: "",

      nombrePac: "",
      apellidoPac: "",
      emailPac: "",
      contraseñaPac: "",
      identificacion: 0,
      switchNoche: 0,

      admin: [],
      iniciales: "",

    }
  },

  mounted() {
    axios.get('/api/pacientes')
      .then(datos => {
        this.pacientes = datos.data;
        this.pacientes = this.pacientes.sort((a, b) => a.apellido - b.apellido);
        // this.console.log(this.pacientes)
      })
      .catch(error => console.warn(error.message));
    axios.get('/api/profesionales')
      .then(data => {
        this.profesionales = data.data;
        this.profesionales = this.profesionales.sort(function (a, b) {
          return a.apellido - b.apellido;
        });
        console.log(this.profesionales)
      })
      .catch(error => console.warn(error.response.data.message));
    axios.get("/api/servicios")
      .then((datos) => {
        this.servicios = datos.data.sort((a, b) => a.id - b.id);
      })
      .catch((error) => console.warn(error.message));
    axios.get("/api/productos")
      .then((datos) => {
        this.productos = datos.data.sort((a, b) => a.id - b.id);
      })
      .catch((error) => console.warn(error.message));


  },

  created() {
    axios.get("/api/pacientes/autenticado")
      .then((data) => {
        this.admin = data.data;
        let firstNameLetter = this.admin.nombre.charAt(0).toUpperCase()
        let secondNameLetter = this.admin.apellido.charAt(0).toUpperCase()
        this.iniciales = firstNameLetter + secondNameLetter
      })
      .catch((error) => console.warn(error.message));



  },

  methods: {
    nuevoProfesional() {
      axios.post("/api/profesional", `nombre=${this.nombrePro}&apellido=${this.apellidoPro}&especialidad=${this.especialidadPro}&servicioId=${this.servicioPro}&email=${this.emailPro}&contraseña=${this.contraseñaPro}`)
        .then(response => {
          location.reload()
          window.alert("Profesional agregado")

        })
        .catch(error => {
          console.log(error)
          window.alert("No se pudo agregar")
        });
    },
    editarNombre(id) {
      axios.patch(`/api/profesional/${id}/nombre`, `nombre=${this.nombrePro}`)
        .then(response => {
          window.location.reload()
          window.alert("nombre editado")
        })
    },

    editarApellido(id) {
      axios.patch(`/api/profesional/${id}/apellido`, `apellido=${this.apellidoPro}`)
        .then(response => {
          window.location.reload()
          window.alert("apellido editado")
        })
    },

    editarEspecialidad(id) {
      axios.patch(`/api/profesional/${id}/especialidad`, `especialidad=${this.especialidadPro}`)
        .then(response => {
          window.location.reload()
          window.alert("especialidad editado")
        })
    },

    editarEmail(id) {
      axios.patch(`/api/profesional/${id}/email`, `email=${this.emailDoc}`)
        .then(response => {
          window.location.reload()
          window.alert("email editado")
        })
    },

    eliminarProfesional(id) {
      axios.patch(`/api/profesional/${id}`)
        .then(response => {
          window.location.reload()
          window.alert("profesional eliminado")
        })
    },
    desactivarPaciente(id) {
      axios.patch(`/api/pacientes/${id}`)
        .then(response => {
          window.location.reload()
          window.alert("Paciente dado de baja")
        })
    },
    logOut() {
      axios.post('/api/logout')
        .then(response => {
          window.location.href = '/web/index.html'
        })

    },
    // modoNoche() {
    //   if (this.switchNoche) {
    //     principal[0].style.color = "#DBDBDB";
    //     principal[0].style.backgroundColor = "#414141";
    //   } else {
    //     principal[0].style.color = "black";
    //     principal[0].style.backgroundColor = "white";
    //   }
    // },
    formatoPrecio(number){
      if(number != undefined){
        let balance = number.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
        return balance;
      }
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
  target.addEventListener('click', () => {
    content.forEach(c => {
      c.classList.remove('active')
    })
    const t = document.querySelector(target.dataset.target)
    t.classList.add('active')
  })
})