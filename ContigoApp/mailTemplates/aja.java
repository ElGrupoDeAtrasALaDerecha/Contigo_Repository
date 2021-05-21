@OnMessage
    public void onMessage(String mensaje, Session sesion) {
        JSONObject obj = new JSONObject(mensaje);
        String tipo = (String) obj.get("tipo");
        JSONObject objRespuesta = new JSONObject();
        Sala sala = null;
        int numeroSala;
        IPersonalCalificadoDao personalDaoConcreto=(IPersonalCalificadoDao)personalDao;
        PersonalCalificado personalCalificado = null;
        try {
            switch (tipo) {
                //Aquí van 100 líneas
            }
        } catch (IOException ex) {
            Logger.getLogger(ContigoBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
