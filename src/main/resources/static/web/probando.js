const app = Vue.createApp({
    data() {
        return {
            productos: [],
            imagenUrl: "",
            base64String: ""
        }
    },
    created() {
        axios.get("/api/productos")
            .then(response => {
                console.log(response)
                this.productos = response.data
                console.log("llegue");
            })
            .catch(error => console.log(error))
    },

    methods: {
        Uploaded() {
            const client = filestack.init("AUEdfex91SIu3V0UjdqDrz");
            const options = {
                onUploadDone: (response) => {
                    console.log(response)
                    this.imagenUrl = response.filesUploaded[0].url
                    this.cargarImagen()
                }
            };

            client.picker(options).open();
        },
        cargarImagen() {
            const urlParams = new URLSearchParams(window.location.search);
            const idAccount = urlParams.get('id');

            console.log(`http://localhost:8080/api/productos/${idAccount}/imagen`, `imagen=${this.imagenUrl}`)
            axios.patch(`http://localhost:8080/api/productos/${idAccount}/imagen`, `imagen=${this.imagenUrl}`)
                .then(response => console.log(response.data))
                .catch(error => console.log(error.data));

        },
        generarFactura() {

            let serviciosatomar = []

            function click(servic) {
                let sv = {
                    idServicio: servic.id,
                    fecha: servic.fecha
                }
                this.serviciosatomar.push(sv)
            }

            this.serviciosatomar.forEach(serv => servicios.push(serv))
            let servicios = []

            let objt = {
                servicios: [],
                productos: []
            }

            axios.post('/api/factura/create', objt)
        }
    },
    computed: {

    },

}).mount('#app')