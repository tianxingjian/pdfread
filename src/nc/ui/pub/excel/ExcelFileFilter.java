package nc.ui.pub.excel;

import java.io.File;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

/**  
 * @author 
 *  �ļ�������    ��������ѡ���ļ�Ϊxls��ʽ��Excel�ļ�
 */  
public class ExcelFileFilter extends FileFilter {   
    private String filters = "xls";   
//    private String filters2 = "xlsx";   
    private String description = "Microsoft Excel (*.xls)";   
    /**  
     * MyFileFilter ������ע�⡣  
     */  
    public ExcelFileFilter() {   
        super();   
    }   
    /**  
     * Whether the given file is accepted by this filter.  
     */  
    public boolean accept(java.io.File f)//�˹������Ƿ���ܸ���ļ���   
    {   
        if (f != null)   
        {   
            if (f.isDirectory())//���Դ˳���·�����ʾ���ļ��Ƿ���һ��Ŀ¼��   
            {   
                return true;   
            }   
            String extension = getExtension(f);   
            if(extension != null && (extension.trim().equals(this.filters)))   
            {   
                return true;   
            }   
        }   
        return false;   
    }   
    /**  
     * The description of this filter. For example: "JPG and GIF Images"  
     * @see FileView#getName  
     */  
    public String getDescription()//�˹�������������   
    {   
        return description;   
    }   
       
    public static String getExtension(File f) {   
        if(f != null) {   
            String filename = f.getName();   
            int i = filename.lastIndexOf('.');   
            if(i>0 && i<filename.length()-1) {   
                return filename.substring(i+1).toLowerCase();   
            };   
        }   
        return null;   
    }   
}  
