<?php
    
class Game_model extends CI_Model {

    function __construct()
    {
        parent::__construct();
    }

    function setCards($parameter){
    	$data['CardID1'] = $parameter['CardID1'];
        $data['CardID2'] = $parameter['CardID2'];
        $data['CardID3'] = $parameter['CardID3'];
        $data['Ready'] = 1;
        $where = "RoomID=".$parameter['RoomID']." AND UserID=".$parameter['UserID'];
        $this->db->where($where);

        $this->db->trans_start();
        $this->db->update('Rooms', $data);
        $this->db->trans_complete(); 
       	if ($this->db->trans_status() === FALSE) {
               //检测Insert是否Fail
               $result['ret'] = 400;
               return $result;
            } 
        $result['ret'] = 200;
        $result['RoomID']=$parameter['RoomID'];
        return $result;
    }

    function isRoomReady($parameter){
    	$this->db->trans_start();
    	$this->db->select('*');
        $this->db->from('Rooms');
        $this->db->where('RoomID',$parameter['RoomID']);
        $query=$this->db->get();
        $NumberOfPlayer = $query->num_rows();
        $this->db->trans_complete(); 
        if ($this->db->trans_status() === FALSE) {
               //检测Insert是否Fail
               $result['ret'] = 400;
               return $result;
        } 

        if($NumberOfPlayer==2){
        	$result['ret'] = 200;
        	$result['RoomID']=$parameter['RoomID'];
        	return $result;
        }
        else{
        	$result['ret']=400;
        	return $result;
        }
    }

    function isFightReady($parameter){
		$this->db->trans_start();
    	$this->db->select('*');
        $this->db->from('Rooms');
        $where = "RoomID=".$parameter['RoomID']." AND Ready='1'";
        $this->db->where($where);
        $query=$this->db->get();
        $query_result=$query->result_array();
        $NumberOfPlayer = $query->num_rows();
        if($NumberOfPlayer==2){
        	$this->load->dbforge();
        	$fields = array(
					        'PlayID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					                'auto_increment' => TRUE,
					        ),
					        'Player1ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'UserID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card1ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card1HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card2ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card2HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card3ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card3HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card1ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card1HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card2ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card2HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card3ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card3HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'FromNum' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'ToNum' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),     
					);
			$this->dbforge->add_field($fields);
			$this->dbforge->add_key('PlayID', TRUE);
			$this->dbforge->create_table('Room'.$parameter['RoomID']);
			$this->db->where('RoomID', $parameter['RoomID']);
     		$this->db->delete('Rooms'); 
     		$data = array(
     			'PlayID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					                'auto_increment' => TRUE,
					        ),
					        'Player1ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'UserID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card1ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card1HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card2ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card2HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card3ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player1Card3HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card1ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card1HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card2ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card2HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card3ID' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'Player2Card3HP' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'FromNum' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),
					        'ToNum' => array(
					                'type' => 'INT',
					                'constraint' => 11,
					        ),     
     			);




			$result['ret'] = 200;
        	$result['RoomID']=$parameter['RoomID'];
        	return $result;
        }
        else{
        	$result['ret'] =400;
        	return $result;
        }

    }


    function applyForFight($paramemter)
    {
        $this->db->trans_start();

        $this->db->select('*');
        $this->db->from('Rooms');
        $query=$this->db->get();
        $NumberOfRooms = $query->num_rows();
        if($NumberOfRooms%2==0){
        	$data=array('Remark'=>1);
       		$this->db->insert('RoomIDGenerator',$data);
        
	        $this->db->select('RoomID');
	        $this->db->from('RoomIDGenerator');
	        $this->db->order_by('RoomID','desc');
	        $this->db->limit(1);

	        $query = $this->db->get();
	        $from_query = $query->row_array();

	        $data = array(
	                'UserID'  => $paramemter['UserID'],  
	                'RoomID'  => $from_query['RoomID'],
	                'Ready' => 0,
	            ); 

		    $this->db->insert('Rooms', $data);
	    }
	    else{
	    	$this->db->select('*');
	    	$this->db->from('(SELECT  `RoomID` , COUNT(  `RoomID` ) AS  `Total` FROM  `Rooms` GROUP BY  `RoomID`) As A');
       		$this->db->where('A.Total',1);
        	$this->db->order_by('RoomID','desc');
        	$this->db->limit(1);
        	$query = $this->db->get();
	        $from_query = $query->row_array();
	        $data = array(
	                'UserID'  => $paramemter['UserID'] ,  
	                'RoomID'  => $from_query['RoomID']
	            ); 

		    $this->db->insert('Rooms', $data);

	    }

       	$this->db->trans_complete(); 
       	if ($this->db->trans_status() === FALSE) {
               //检测Insert是否Fail
               $result['ret'] = 400;
               return $result;
            } 
        $result['ret'] = 200;
        $result['RoomID']=$from_query['RoomID'];
        return $result;
    }


}