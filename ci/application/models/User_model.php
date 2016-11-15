<?php
    
class User_model extends CI_Model {

    function __construct()
    {
        parent::__construct();
    }

    function getall(){
        $this->db->select('*');
        $this->db->from('User');  
        $query = $this->db->get();
        return  $query->result(); 
    }

    function register($paramemter){
        $query = $this->db->get_where('User', array('UserName' => $paramemter['UserName']),   1);
        if ($query->num_rows() > 0)
        {
            $result['ret'] = 401;
            return $result;
        }
        else
        {
   
            $data = array(
                'UserName'    => $paramemter['UserName'] ,  
                'UserIconID'  => $paramemter['UserIconID'],
                'Password'    => $paramemter['Password'],
                'Nickname'    => $paramemter['Nickname'],
                'Alliance'    => $paramemter['Alliance'],
                'PositionX'   => $paramemter['PositionX'],
                'PositionY'   => $paramemter['PositionY'] ,
                'WalkDistance'=>$paramemter['WalkDistance'],
                'GpsLat'      => $paramemter['GpsLat'],
                'GpsLon'      => $paramemter['GpsLon']
            );
            $this->db->trans_start();
            $this->db->insert('User', $data);
            $this->db->trans_complete(); 
            if ($this->db->trans_status() === FALSE) {
               //检测Insert是否Fail
               $result['ret'] = 400;
               return $result;
            } else {
               
            }
            $result['ret'] = 200;
            return $result;

        }

    }

    function login($paramemter)
    {

        $query = $this->db->get_where('User', array('UserName' => $paramemter['UserName']),   1);
        if ($query->num_rows() > 0)
        {
            $result['UserInfo'] = $query->row_array(); 

            $result['ret']=200;
            
            $password = $paramemter['Password'];
            if($result['UserInfo']['Password'] != $password){
                $result['ret'] = 401;
            }
            
            return $result;
        }else{
           
            $result['ret']=404;
            return $result;
        }
    }
 
}
