package co.com.hbt.koba.excepciones;

/**
 * 
 * Clase responsable de Manejar las excepciones tecnicas
 *
 * @uthor hataborda <br>
 *        Harold Taborda <br>
 *        hataborda@heinsohn.com.co
 *
 * @date 17/01/2020
 * @version 1.0
 */
public class ExcepcionTecnica extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -4559281328845536415L;
    /**
     * Constructor por defecto
     */
    public ExcepcionTecnica() {
        super();
    }


    /**
     * Constructor que inicializa la clase con un objeto de tipo CodigosError y un
     * objeto de tipo de lanzamiento de error
     * 
     * @author HATABORDA <br>
     *         Juan Camilo Restrepo Mejia<br>
     * 
     * @date 03/05/2016
     * @version 1.0
     * 
     * @param error
     * @param causa
     */
    public ExcepcionTecnica(CodigosError error, Throwable causa) {
        super(error.getMensaje(), causa);
    }

    /**
     * Constructor que inicializa la clase con un objeto de tipo CodigosError y un
     * objeto de tipo de lanzamiento de error
     * 
     * @author HATABORDA <br>
     *         Juan Camilo Restrepo Mejia<br>
     * 
     * @date 03/05/2016
     * @version 1.0
     * 
     * @param error
     * @param causa
     */
    public ExcepcionTecnica(CodigosError error) {
        super(error.getMensaje());
    }

}
