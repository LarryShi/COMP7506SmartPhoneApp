<?php
    
class server extends CI_Model {

    function __construct()
    {
        parent::__construct();
    }

    function getall(){
        $this->db->select('*');
        $this->db->from('mytable');  
        $query = $this->db->get();
        return  $query->result(); 
    }
 
}
