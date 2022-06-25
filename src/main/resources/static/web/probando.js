const app = Vue.createApp({
    data() {
        return {
            imagenUrl: "",
            base64String: ""
        }
    },

    mounted() {

    },

    created() {
        console.log("xd")
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
            console.log(`http://localhost:8080/api/productos/${idAccount}/imagen`, this.imagenUrl)
            axios.patch(`http://localhost:8080/api/productos/${idAccount}/imagen`, `imagen=${this.imagenUrl}`)
                .then(response => console.log(response.data))
                .catch(error => console.log(error.data));
        }
    }

}).mount('#app')