const app = Vue.createApp({
    data() {
        return {
            paciente: [],
            datosCards: [],

            gProductosEnStorage: [],
            gCarrito: [],

            productoCarrito: [],

            gTotalEnCarrito: 0,
            medioDePago: "", // Precio total de la compra (sub-total)

            cardnumber: "",
            securityCode: "",
            amount: 0,


        }
    },

    mounted() {
        // ===== Scroll to Top ==== 
        $(window).scroll(function () {
            if ($(this).scrollTop() >= 1080) { // If page is scrolled more than 50px
                $('#return-to-top').fadeIn(500); // Fade in the arrow
            } else {
                $('#return-to-top').fadeOut(500); // Else fade out the arrow
            }
        });

        $('#return-to-top').click(function () { // When arrow is clicked
            $('body,html').animate({
                scrollTop: 0 // Scroll to top of body
            }, 500);
        });

        // Loader
        $(document).ready(function preloaderSetup() {
            $(".st-perloader").fadeOut();
            $("st-perloader-in").delay(150).fadeOut("slow");
        })


    },

    created() {
        axios.get("/api/productos")
            .then(datos => {
                this.datosCards = datos.data;

                console.log(this.datosCards);


                // STORAGE
                this.gProductosEnStorage = JSON.parse(localStorage.getItem("carrito"));

                // console.log(this.gProductosEnStorage)
                if (this.gProductosEnStorage) {
                    this.gCarrito = this.gProductosEnStorage
                }

                this.gTotalEnCarrito = this.gCarrito.map(prod => prod.precio * prod.cantidad).reduce((a, b) => a + b, 0);
                // console.log(this.gCarrito);
            })
            .catch(error => console.log(error))
        // this.generarCompra()

        axios.get("/api/pacientes/autenticado")
            .then(response => {
                this.paciente = response.data;
                console.log(this.paciente)
            })
    },

    methods: {
        valorCantidadProducto(producto, e) {
            producto.cantidad = e.target.value;
            producto.stock -= e.target.value;
        },

        formatMoney(amount) {
            return new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: 'USD',
                minimumFractionDigits: 2
            }).format(amount);
        },

        removeItem(producto) {
            this.productoCarrito = this.gCarrito.filter(prod => producto.id == prod.id)[0]

            let index = this.gCarrito.findIndex(prod => prod.id == producto.id);
            // SI EL OBJETO PRODUCTO TIENE CANTIDAD MAYOR A 1, SE DECREMENTA UNO.
            if (this.productoCarrito.cantidad > 1) {
                this.productoCarrito.cantidad--
                this.productoCarrito.stock++
                this.gCarrito[index].stock++

                // this.productosEnCarrito.slice(index,this.productoCarrito)
            }
            // SINO, SE ELIMINA ESE OBJETO DEL ARRAY DE PRODUCTOS FILTRANDO LOS DISTINTOS AL SELECCIONADO
            else {
                this.gCarrito = this.gCarrito.filter(prod => prod.id != producto.id)
                this.productoCarrito.stock++
                Swal.fire({
                    title: 'Eliminado del Carrito',
                    imageUrl: 'https://cdn-icons-png.flaticon.com/512/105/105739.png',
                    imageHeight: 80,
                })
            }
            // SE ACTUALIZA EL LOCAL STORAGE CON EL ARRAY MODIFICADO SI FUESE EL CASO
            this.productosEnStorage = this.gCarrito
            // ACA SE VUELVE A CALCULAR EL TOTAL DE PRODUCTOS QUE QUEDARON EN EL CARRITO
            this.gTotalEnCarrito = this.gCarrito.map(prod => prod.precio * prod.cantidad).reduce((a, b) => a + b, 0)


            localStorage.setItem("carrito", JSON.stringify(this.productosEnStorage))

        },

        generarCompra() {

            let productoss = [];
            this.gCarrito.forEach(producto => {

                let aux = {
                    idProducto: parseInt(producto.id),
                    cantidad: parseInt(producto.cantidad)
                }
                productoss.push(aux)
            })

            let objt = {
                servicios: [],
                productos: productoss
            }

            axios.post('/api/facturas/create', objt)
                .then(response => {
                    console.log("equisde")
                    this.gCarrito = []

                    // SE ACTUALIZA EL LOCAL STORAGE CON EL ARRAY MODIFICADO SI FUESE EL CASO
                    this.productosEnStorage = this.gCarrito
                    localStorage.setItem("carrito", JSON.stringify(this.productosEnStorage))

                    Swal.fire({
                        title: 'Pago aprobado!',
                        icon: 'success',
                        html: '<p>Tu pago ha sido aprobado, por favor verifique su cuenta.</p>',
                        confirmButtonText: 'Ir a tienda',
                        showDenyButton: true,
                        denyButtonText: `Descargar comprobante`,
                    }).then((result) => {
                        /* Read more about isConfirmed, isDenied below */
                        if (result.isConfirmed) {
                            window.location.href = "./pacientes.html"
                        } else if (result.isDenied) {
                            axios.post('/api/facturas/descargar', `id=${this.paciente.id}`, {
                                    'responseType': 'blob'
                                })
                                .then(response => {
                                    let url = window.URL.createObjectURL(new Blob([response.data]))
                                    let link = document.createElement("a")
                                    link.href = url;
                                    link.setAttribute("download", `Comprobante_Compra_${new Date().toISOString().slice(0, 10)}.pdf`)
                                    document.body.appendChild(link)
                                    link.click()
                                    setTimeout(window.location.href = "./pacientes.html", 5000)
                                })

                                .catch(error => {

                                    Swal.fire('Download Failed', '', 'error')
                                        .then(result => {
                                            window.location.reload()
                                        })
                                })
                        }
                    })

                })
                .catch(error => {
                    console.log(error.response)
                })

            // let serviciosatomar = []

            // function click(servic) {
            //     let sv = {
            //         idServicio: servic.id,
            //         fecha: servic.fecha
            //     }
            //     this.serviciosatomar.push(sv)
            // }

            // this.serviciosatomar.forEach(serv => servicios.push(serv))
            // let servicios = []

            // let objt = {
            //     servicios: [],
            //     productos: []
            // }

            // axios.post('/api/factura/create', objt)

            // {
            //     "servicios":[
            //         {"idServicio":1,"fecha":"2022-06-27T13:50:33","idProfesional":1},
            //         {"idServicio":2,"fecha":"2022-06-28T13:50:33","idProfesional":2},
            //         {"idServicio":3,"fecha":"2022-06-29T13:50:33","idProfesional":3} ]
            //     ,
            //     "productos":[
            //         {"idProducto":5,"cantidad":5},
            //         {"idProducto":1,"cantidad":1},
            //         {"idProducto":3,"cantidad":2} ]
            //     }
        },

        generarPago() {

            console.log({
                numberCard: this.cardnumber.replace(/\s+/g, ''),
                cvv: parseInt(this.securityCode),
                amount: this.gTotalEnCarrito,
                description: `Compra tienda Medihub`
            });

            Swal.fire({
                title: 'Estas seguro?',
                text: "Una vez confirmado el pago, no hay reembolso",
                icon: 'warning',
                showCancelButton: true,
                cancelButtonText: "Cancelar",
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, Confirmar pago!'
            }).then((result) => {
                if (result.isConfirmed) {

                    let config = {
                        headers: {
                            'Access-Control-Allow-Origin': '*',
                        }
                    }
                    let paymentDTO = {
                        numberCard: this.cardnumber.replace(/\s+/g, ''),
                        cvv: parseInt(this.securityCode),
                        amount: this.gTotalEnCarrito,
                        description: `Compra tienda Medihub`
                    }
                    let path = "https://danobank.herokuapp.com/api/transactions/payment"
                    axios.post(path, paymentDTO, config)

                        .then(response => {
                            this.generarCompra()
                        })
                        .catch(error => {
                            Swal.fire({
                                title: 'Error en la compra!',
                                icon: 'error',
                                html: `<p>Your payment has been rejected
                                , ${error.response.data}</p>`,
                                confirmButtonText: 'Reintentar',
                                showCancelButton: true,
                                cancelButtonText: "Cancelar"
                            }).then((result) => {
                                /* Read more about isConfirmed, isDenied below */
                                if (result.isConfirmed) {

                                } else {
                                    window.location.href = './index.html'
                                }
                            })
                        })
                }
            })

        }


    },

    computed: {

    }

}).mount('#app')


/* Set rates + misc */
var taxRate = 0.05;
var shippingRate = 15.00;
var fadeTime = 300;


/* Assign actions */
$('.product-quantity input').change(function () {
    updateQuantity(this);
});

$('.product-removal button').click(function () {
    removeItem(this);
});


/* Recalculate cart */
function recalculateCart() {
    var subtotal = 0;

    /* Sum up row totals */
    $('.product').each(function () {
        subtotal += parseFloat($(this).children('.product-line-price').text());
    });

    /* Calculate totals */
    var tax = subtotal * taxRate;
    var shipping = (subtotal > 0 ? shippingRate : 0);
    var total = subtotal + tax + shipping;

    /* Update totals display */
    $('.totals-value').fadeOut(fadeTime, function () {
        $('#cart-subtotal').html(subtotal.toFixed(2));
        $('#cart-tax').html(tax.toFixed(2));
        $('#cart-shipping').html(shipping.toFixed(2));
        $('#cart-total').html(total.toFixed(2));
        if (total == 0) {
            $('.checkout').fadeOut(fadeTime);
        } else {
            $('.checkout').fadeIn(fadeTime);
        }
        $('.totals-value').fadeIn(fadeTime);
    });
}


/* Update quantity */
function updateQuantity(quantityInput) {
    /* Calculate line price */
    var productRow = $(quantityInput).parent().parent();
    var price = parseFloat(productRow.children('.product-price').text());
    var quantity = $(quantityInput).val();
    var linePrice = price * quantity;

    /* Update line price display and recalc cart totals */
    productRow.children('.product-line-price').each(function () {
        $(this).fadeOut(fadeTime, function () {
            $(this).text(linePrice.toFixed(2));
            recalculateCart();
            $(this).fadeIn(fadeTime);
        });
    });
}


/* Remove item from cart */
function removeItem(removeButton) {
    /* Remove row from DOM and recalc cart total */
    var productRow = $(removeButton).parent().parent();
    productRow.slideUp(fadeTime, function () {
        productRow.remove();
        recalculateCart();
    });
}