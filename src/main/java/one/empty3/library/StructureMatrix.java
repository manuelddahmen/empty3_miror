/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package one.empty3.library;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/*__
 * Created by manue on 07-09-19.
 */
public class StructureMatrix<T> {
    private static final int INSERT_ROW = 0;
    private static final int INSERT_COL = 1;
    private int dim;
    public T data0d;
    public List<T> data1d;
    public List<List<T>> data2d;
    private Class<?> classType;
    private StructureMatrix<Point3D> all;
    private Point3D center;

    public StructureMatrix() {
        dim = -1;

    }

    public void setClassType(Class T) {
        classType = T;
    }

    public StructureMatrix(int dim, Class classType) {
        init(dim, classType);
    }


    public void init(int dim, Class classType) {
        this.dim = dim;
        if (dim == 1)
            data1d = Collections.synchronizedList(new ArrayList<>());
        if (dim == 2)
            data2d = Collections.synchronizedList(new ArrayList<>());
        this.classType = classType;
    }

    public void setElem(T value) {
        dim = 0;
        this.data0d = value;
        this.classType = value.getClass();
        listenersPropertyChanged(null, value);
    }

    public void setElem(T elem, int i) {
        if (i >= data1d.size()) {
            int j = data1d.size();
            while (j <= i) {
                if (getClassType().equals(Boolean.class)) {
                    data1d.add((T) Boolean.FALSE);
                } else if (getClassType().equals(Integer.class)) {
                    data1d.add((T) (Integer) 0);
                } else if (getClassType().equals(Double.class)) {
                    data1d.add((T) (Double) 0.);
                } else if (getClassType().equals(String.class)) {
                    data1d.add((T) "");
                } else {
                    data1d.add(null);
                }
                j++;
            }
        }
        getData1d().set(i, elem);
        listenersPropertyChanged(null, elem);
    }

    public void setElem(T elem, int i, int j) {

        this.classType = elem.getClass();
        if (data2d == null)
            data2d = Collections.synchronizedList(new ArrayList<>());
        while (i >= data2d.size()) {
            data2d.add(Collections.synchronizedList(new ArrayList<>()));
        }
        while (j >= data2d.get(i).size()) {
            data2d.get(i).add(elem);
        }
        data2d.get(i).set(j, elem);
        listenersPropertyChanged(null, elem);
    }

    public T getElem(int[] indices) {
        if (dim == 0) {
            return this.data0d;
        }
        if (dim == 1) {
            return data1d.get(indices[0]);
        }
        if (dim == 2) {
            return data2d.get(indices[0]).get(indices[1]);
        }
        return null;
    }

    public T getElem() {

        if (dim == 0)
            return this.data0d;
        System.err.println("getElem dim= " + dim + "!=0");
        return null;
    }

    public T getElem(int i) {
        if (dim == 1) {
            return data1d.get(i);
        }
        System.err.println("getElem dim= " + dim + "!=1");
        return null;
    }

    public T getElem(int i, int j) {
        if (dim == 2) {
            return data2d.get(i).get(j);
        }
        System.err.println("getElem dim= " + dim + "!=2");
        return null;
    }

    public T getData0d() {
        return data0d;
    }

    public List<T> getData1d() {
        return data1d;
    }

    public List<List<T>> getData2d() {
        return data2d;
    }

    public void insert(int pos, int rowCol, T value) {
        if (dim == 1)
            data1d.add(pos, value);
        if (dim == 2) {
            if (rowCol == INSERT_ROW) {
                List<T> ins = Collections.synchronizedList(new ArrayList<T>());
                for (int i = 0; i < data2d.get(0).size(); i++)
                    ins.add(value);
                data2d.add(pos, ins);

            } else if (rowCol == INSERT_COL) {
                for (int i = 0; i < data2d.size(); i++) {
                    data2d.get(pos).add(i, value);
                }
            }
        }
        listenersPropertyChanged(null, value);
    }

    public void delete(int pos, int rowCol) {
        if (dim == 1)
            data1d.remove(pos);
        if (dim == 2) {
            if (rowCol == INSERT_ROW) {
                data2d.remove(pos);

            } else if (rowCol == INSERT_COL) {
                for (int i = 0; i < data2d.size(); i++) {
                    data2d.get(pos).remove(i);
                }
            }
        }
        listenersPropertyChanged(null, null);
    }

    public void delete(int pos) {
        if (this.dim == 1) {
            this.data1d.remove(pos);
        }

    }

    public void insert(int i, T value) {
        if (dim == 1)
            data1d.add(i, value);
        listenersPropertyChanged(null, value);
    }

    public void add(int dim, T value) {
        if (this.dim == 0) {
            data0d = value;
        }
        if (this.dim == 1) {
            data1d.add(value);
        }
        if (this.dim == 2) {
            int ind1 = data2d.size();
            data2d.get(ind1).add(value);
        }
        listenersPropertyChanged(null, value);
    }

    public void add(T value) {
        if (this.dim == 0) {
            //data0d = value;
        }
        if (this.dim == 1) {
            data1d.add(value);
        }
        if (this.dim == 2) {
            //int ind1 = data2d.size();
            //data2d.get(ind1).add(value);
        }
        listenersPropertyChanged(null, value);
    }

    public void addRow() {
        if (this.dim == 2) {
            data2d.add(Collections.synchronizedList(new ArrayList<T>()));
        }
        listenersPropertyChanged(null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StructureMatrix<?> that = (StructureMatrix<?>) o;

        if (dim != that.dim) return false;
        if (dim == 0 && !data0d.equals(that.data0d)) return false;
        if (dim == 1 && !data1d.equals(that.data1d)) return false;
        if (dim == 2 && !data2d.equals(that.data2d)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = dim;
        result = 31 * result + data0d.hashCode();
        result = 31 * result + data1d.hashCode();
        result = 31 * result + data2d.hashCode();
        return result;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("structure(\n");
        s.append("(dim:" + dim + ")");
        switch (dim) {
            case 0:
                s.append("(data : {" + data0d.toString() + "} )");
                break;
            case 1:
                s.append("(data : (");
                data1d.forEach(new Consumer<T>() {
                    @Override
                    public void accept(T t) {
                        s.append("(" + t.toString() + ")");
                    }
                });
                break;
            case 2:
                s.append("(data : (");
                data2d.forEach(new Consumer<List<T>>() {
                    @Override
                    public void accept(List<T> ts) {
                        s.append("( ");

                        ts.forEach(new Consumer<T>() {
                            @Override
                            public void accept(T t) {
                                s.append("(" + t.toString() + ")");
                            }
                        });

                        s.append(" )\n");

                    }
                });
                s.append("\n)\n");
                break;


        }
        return s.toString();
    }


    public Class getClassType() {
        return classType;
    }

    public void setAll(T[] all) {
        data1d = Collections.synchronizedList(new ArrayList<>());
        switch (dim) {
            case 1:
                for (T t : all) {
                    if (t != null)
                        data1d.add(t);
                    else
                        throw new NullPointerException("setAll elem == null");
                }
        }
    }

    public void setAll(T[][] all) {
        data2d = Collections.synchronizedList(new ArrayList<List<T>>());
        switch (dim) {
            case 2:
                for (int i = 0; i < all.length; i++) {
                    for (int j = 0; j < all[0].length; j++)
                        setElem(all[i][j], i, j);
                }

        }
        listenersPropertyChanged(null, null);
    }

    public void setAll(ArrayList<T> all) {
        dim = 1;
        this.data1d = all;
    }

    public void reset() {
        data0d = null;
        if (dim == 1)
            data1d.clear();
        if (dim == 2)
            data2d.clear();
        listenersPropertyChanged(null, null);
    }

    private void listenersPropertyChanged(Object oldValue, Object newValue) {
        if (listeners.size() > 0) {
            listeners.forEach(listener ->
                    listener.actionOnChange(oldValue, newValue));
        }
    }

    List<StructureMatrixListener> listeners = Collections.synchronizedList(new ArrayList<>());

    public void addListener(StructureMatrixListener listener) {
        listeners.add(listener);
    }

    public void deleteListener(StructureMatrixListener listener) {
        listeners.remove(listener);
    }

    private T cloneElement(T t) throws IllegalAccessException, CopyRepresentableError, InstantiationException {
        T t1 = null;
        if(t==null)
            return null;
        if (t instanceof StructureMatrix) {
            t1 = (T) ((StructureMatrix) t).copy();
        } else if(t instanceof Point3D) {
            t1 = (T) new Point3D(((Point3D)t).get(0),
                    ((Point3D)t).get(1),((Point3D)t).get(2));
        } else if (t instanceof MatrixPropertiesObject) {
            ((MatrixPropertiesObject) t).declareProperties();
            t1 = (T) ((MatrixPropertiesObject) t).copy();
        }
        return t1;
    }

    public StructureMatrix<T> copy() throws IllegalAccessException, CopyRepresentableError, InstantiationException {
        StructureMatrix<T> tStructureMatrix = new StructureMatrix<T>(this.getDim(), this.classType);
        switch (getDim()) {
            case 0 -> tStructureMatrix.data0d = cloneElement(data0d);
            case 1 -> {
                tStructureMatrix.data1d = new ArrayList<>();
                if (data1d != null)
                    data1d.forEach(new Consumer<T>() {
                        @Override
                        public void accept(T t) {
                            try {
                                tStructureMatrix.add(1, cloneElement(t));
                            } catch (IllegalAccessException | CopyRepresentableError | InstantiationException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            }
            case 2 -> {
                if (data2d != null) {

                    data2d.forEach(new Consumer<List<T>>() {
                        @Override
                        public void accept(List<T> ts) {
                            ArrayList<T> objects = new ArrayList<>();
                            tStructureMatrix.data2d.add(objects);
                            ts.forEach(new Consumer<T>() {
                                @Override
                                public void accept(T t) {
                                    try {
                                        objects.add(cloneElement(t));
                                    } catch (IllegalAccessException | CopyRepresentableError |
                                             InstantiationException e) {
                                        e.printStackTrace();
                                    }


                                }
                            });

                        }

                    });
                }
            }
        }
        return tStructureMatrix;

    }

}
