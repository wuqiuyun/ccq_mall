package per.sue.gear2.utils;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import per.sue.gear2.annotation.Id;


/*
* 文件名：
* 描 述：
* 作 者：苏昭强
* 时 间：2015/12/16
*/
public class AnnListUtils {

    public static boolean removeById(List<? extends  Object> list, Object obj)  {
        boolean  success = false;
        if(null != list){
            Iterator iterator =  list.iterator();
            while(iterator.hasNext()){
                Object bean = iterator.next();
                if(isEqualById(bean, obj)){
                    iterator.remove();
                    success = true;
                }
            }
        }
        return success;
    }

    public static boolean containById(List<? extends  Object> list, Object obj)  {
        boolean  contain = false;
        if(null != list){
            for(Object bean : list){
                if(isEqualById(bean, obj)){
                    contain = true;
                }
            }
        }
        return contain;
    }


    public static boolean isEqualById(Object object1, Object object2 ) {

        return isEqualByAnnotation(object1, object2, Id.class );
    }


    public static boolean removeByAnnotation(List<? extends  Object> list, Object obj, Class  annotationCls)  {
        boolean  success = false;
        if(null != list){
            Iterator iterator =  list.iterator();
            while(iterator.hasNext()){
                Object bean = iterator.next();
                if(isEqualByAnnotation(bean, obj, annotationCls)){
                    iterator.remove();
                    success = true;
                }
            }
        }
        return success;
    }

    public static boolean containByAnnotation(List<? extends  Object> list, Object obj, Class  annotationCls)  {
        boolean  contain = false;
        if(null != list){
            for(Object bean : list){
                if(isEqualByAnnotation(bean, obj, annotationCls)){
                    contain = true;
                }
            }
        }
        return contain;
    }

    public static boolean isEqualByAnnotation(Object object1, Object object2 , Class  annotationCls) {
        boolean isEqual = false;
        if(null  == object1 || null == object2 ){
            return false;
        }
        Field[] fields = object1.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (null != field.getAnnotation(annotationCls)) {
                field.setAccessible(true);
                Field field2 = null;
                try {
                    field2 = object2.getClass().getDeclaredField(field.getName());
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                field2.setAccessible(true);
                try {
                    //System.out.println(field.getName()+field.get(object1).toString());
                    //System.out.println(field2.getName() + field2.get(object2).toString());
                    if(null == field.get(object1) || null == field2.get(object2)){
                        return false;
                    }
                    if (field.get(object1).equals(field2.get(object2))) {
                        isEqual = true;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return isEqual;
    }


   /* public static void  main(String[] args){
        ArrayList<Student> strs = new ArrayList<>();
        strs.add(new Student("1","22'"));
        strs.add(new Student("3","22'"));
        strs.add(new Student("56"));
        Student student = new Student("1","很哈");
        System.out.print(containById(strs,student)+"ID匹配");
        System.out.print(containByAnnotation(strs, new Student("57"), UserId.class)+"userID匹配");
    }*/


}
