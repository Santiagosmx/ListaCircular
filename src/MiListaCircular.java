public class MiListaCircular implements ListInterface {

    Node cabeza;
    Node cola;
    int size;

    // Constructor por defecto
    public MiListaCircular() {
        this.cabeza = null;
        this.cola = null;
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.cabeza == null;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void clear() {
        this.cabeza = null;
        this.cola = null;
        this.size = 0;
    }

    @Override
    public Object getHead() {
        if (cabeza == null) {
            return null;
        }

        return cabeza.dato;
    }

    @Override
    public Object getTail() {
        if (cola == null) {
            return null;
        }
        return cola.dato;
    }

    @Override
    public Object get(Node node) {
        if (cabeza == null || node == null) {
            return null;
        }

        Node iterador = cabeza;

        do {
            if (iterador == node) {
                return iterador.dato;
            }

            iterador = iterador.siguiente;
        } while (iterador != cabeza);

        return null;
    }

    @Override
    public Node search(Object object) {
        if (cabeza == null || object == null) {
            return null;
        }

        Node iterador = cabeza;

        do {
            if (iterador.dato.equals(object)) {
                return iterador;
            }

            iterador = iterador.siguiente;
        } while (iterador != cabeza);

        return null;
    }

    @Override
    public boolean add(Object object) {
        return insertTail(object);
    }

    @Override
    public boolean insert(Node node, Object object) {
        if (cabeza == null || node == null) {
            return false;
        }

        Node iterador = cabeza;

        do {
            if (iterador == node) {
                Node nuevoNodo = new Node(object);

                nuevoNodo.siguiente = iterador.siguiente;
                iterador.siguiente = nuevoNodo;

                if (iterador == cola) {
                    cola = nuevoNodo;
                }

                size++;
                return true;
            }

            iterador = iterador.siguiente;
        } while (iterador != cabeza);

        return false;
    }

    @Override
    public boolean insert(Object objectRef, Object object) {
        Node nodo = search(objectRef);

        if (nodo == null) {
            return false;
        }

        return insert(nodo, object);
    }

    @Override
    public boolean insertHead(Object object) {
        Node nuevoNodo = new Node(object);

        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
            nuevoNodo.siguiente = cabeza;
        } else {
            nuevoNodo.siguiente = cabeza;
            cabeza = nuevoNodo;
            cola.siguiente = cabeza;
        }

        size++;
        return true;
    }

    @Override
    public boolean insertTail(Object object) {
        Node nuevoNodo = new Node(object);

        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
            nuevoNodo.siguiente = cabeza;
        } else {
            nuevoNodo.siguiente = cabeza;
            cola.siguiente = nuevoNodo;
            cola = nuevoNodo;
        }

        size++;
        return true;
    }

    @Override
    public boolean set(Node node, Object object) {
        if (cabeza == null || node == null) {
            return false;
        }

        Node iterador = cabeza;

        do {
            if (iterador == node) {
                iterador.dato = object;
                return true;
            }

            iterador = iterador.siguiente;
        } while (iterador != cabeza);

        return false;
    }

    @Override
    public boolean remove(Node node) {
        if (cabeza == null || node == null) {
            return false;
        }

        Node anterior = cola;
        Node iterador = cabeza;

        do {
            if (iterador == node) {
                // Si es el único nodo en la lista
                if (cabeza == cola) {
                    cabeza = null;
                    cola = null;
                }
                // Si el nodo a eliminar es la cabeza pero hay más elementos
                else if (iterador == cabeza) {
                    cabeza = cabeza.siguiente;
                    cola.siguiente = cabeza;
                }
                // Si el nodo está en el medio o es la cola
                else {
                    anterior.siguiente = iterador.siguiente;
                    if (iterador == cola) {
                        cola = anterior;
                    }
                }

                size--;
                return true;
            }

            anterior = iterador;
            iterador = iterador.siguiente;
        } while (iterador != cabeza);

        return false;
    }

    /*
    =========================================
    OPERACIONES DE EXPANSIÓN
    =========================================
    */

    @Override
    public boolean contains(Object object) {
        if (cabeza == null) {
            return false;
        }

        Node iterador = cabeza;

        do {
            if (iterador.dato.equals(object)) {
                return true;
            }

            iterador = iterador.siguiente;
        } while (iterador != cabeza);

        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];

        if (cabeza == null) {
            return array;
        }

        Node iterador = cabeza;
        int i = 0;

        do {
            array[i] = iterador.dato;
            iterador = iterador.siguiente;
            i++;
        } while (iterador != cabeza);

        return array;
    }

    @Override
    public Object[] toArray(Object[] object) {
        if (object.length < size) {
            object = new Object[size];
        }

        if (cabeza != null) {
            Node iterador = cabeza;
            int i = 0;
            do {
                object[i] = iterador.dato;
                iterador = iterador.siguiente;
                i++;
            } while (iterador != cabeza);

            // Estándar de Java: si el array proporcionado es más grande que la lista,
            // el elemento contiguo al final se establece en null
            if (object.length > size) {
                object[size] = null;
            }
        }

        return object;
    }

    @Override
    public MiListaCircular subList(Node from, Node to) {
        MiListaCircular nuevaLista = new MiListaCircular();

        if (from == null || to == null || cabeza == null) {
            return nuevaLista;
        }

        Node iterador = from;

        do {
            nuevaLista.insertTail(iterador.dato);

            if (iterador == to) {
                break;
            }
            iterador = iterador.siguiente;

        } while (iterador != from);

        return nuevaLista;
    }

    @Override
    public MiListaCircular sortList() {
        MiListaCircular nuevaLista = new MiListaCircular();

        if (cabeza == null) {
            return nuevaLista;
        }

        Node iterador = cabeza;
        do {
            nuevaLista.insertTail(iterador.dato);
            iterador = iterador.siguiente;
        } while (iterador != cabeza);

        if (nuevaLista.size > 1) {
            boolean huboIntercambio;
            do {
                huboIntercambio = false;
                Node actual = nuevaLista.cabeza;

                while (actual.siguiente != nuevaLista.cabeza) {

                    Comparable obj1 = (Comparable) actual.dato;
                    Comparable obj2 = (Comparable) actual.siguiente.dato;

                    if (obj1.compareTo(obj2) > 0) {
                        Object temp = actual.dato;
                        actual.dato = actual.siguiente.dato;
                        actual.siguiente.dato = temp;

                        huboIntercambio = true;
                    }
                    actual = actual.siguiente;
                }
            } while (huboIntercambio);
        }

        return nuevaLista;
    }
}
