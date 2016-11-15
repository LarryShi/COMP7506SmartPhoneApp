<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Server extends CI_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see https://codeigniter.com/user_guide/general/urls.html
	 */


	public function index()
	{

		$this->load->model('user_model','',true);
		$content_data['display_value']=$this->user_model->getall();

		$this->load->view('server',$content_data);

	}

	public function user(){
		$this->load->model('user_model','',true);
		$content_data['user']=$this->user_model->getall();

		$this->output
        ->set_content_type('application/json')
        ->set_output(json_encode($content_data));
	}

	public function register()
	{
		$json_string = $this->input->post();
		
		$this->load->model('user_model','',true);
		//传过来的是JSON String，用下面这句
    	//$json = json_decode($json_string, true);
    	//传过来的是JSON Object，用下面这句
    
		$json = $json_string;
		$paramemter['UserName'] = $json['UserName'];
        $paramemter['Password'] = md5($json['Password']);
        $paramemter['UserIconID'] = '1';
        $paramemter['Nickname']= $json['Nickname'];
        $paramemter['Alliance']=$json['Alliance'];
        $paramemter['PositionX']='20';
        $paramemter['PositionY']='20';
        $paramemter['WalkDistance']='0';
        $paramemter['GpsLat']='0';
        $paramemter['GpsLon']='0';
        $data['code']="1";
        if($paramemter['UserName']!=""&&$paramemter['Password']!=""){
			$result = $this->user_model->register($paramemter);
			if($result['ret'] === 200)
			{
				$data['code'] = 0;
				$data['message'] = "Successful";	
			}
			else if($result['ret'] === 401)
			{
				$data['code'] = 2;
				$data['message'] = "Used UserName";
			}
			else{
				$data['code'] = 3;
				$data['message'] = "Fail";		
			}
		}
		$content_data['display_value']['Link']="http://i.cs.hku.hk/~zqshi/ci/index.php/Server/registerM";
		
		$content_data['display_value']['Input']=json_encode($json_string, true);

		$content_data['display_value']['Return']=json_encode($data);

		$this->load->view('result',$content_data);
		
	}

	public function registerM()
	{
		$json_string = $this->input->raw_input_stream;
		
		$this->load->model('user_model','',true);
		//传过来的是JSON String，用下面这句
    	$json = json_decode($json_string, true);
    	//传过来的是JSON Object，用下面这句
		//$json = $json_string;
		$paramemter['UserName'] = $json['UserName'];
        $paramemter['Password'] = md5($json['Password']);
        $paramemter['UserIconID'] = '1';
        $paramemter['Nickname']= $json['Nickname'];
        $paramemter['Alliance']=$json['Alliance'];
        $paramemter['PositionX']='20';
        $paramemter['PositionY']='20';
        $paramemter['WalkDistance']='0';
        $paramemter['GpsLat']='0';
        $paramemter['GpsLon']='0';
        $data['code']="1";
        if($paramemter['UserName']!=""&&$paramemter['Password']!=""){
			$result = $this->user_model->register($paramemter);
			if($result['ret'] === 200)
			{
				$data['code'] = 0;
				$data['message'] = "Successful";
				$this->output
	        			->set_content_type('application/json')
	        			->set_output(json_encode($data));
				
			}
			else if($result['ret'] === 401)
			{
				$data['code'] = 2;
				$data['message'] = "Used UserName";
				$this->output
	        			->set_content_type('application/json')
	        			->set_output(json_encode($data));
			}
			else{
				$data['code'] = 3;
				$data['message'] = "Fail";
				$this->output
	        			->set_content_type('application/json')
	        			->set_output(json_encode($data));
				
			}
		}
	}

	public function login()
	{
		$json_string = $this->input->post();
		
		$this->load->model('user_model','',true);
		//传过来的是JSON String，用下面这句
    	//$json = json_decode($json_string, true);
    	//传过来的是JSON Object，用下面这句
		$json = $json_string;

		$paramemter['UserName'] = $json['UserName'];
        $paramemter['Password'] = md5($json['Password']);
		$data['code']="1";
		if($paramemter['UserName']!=""&&$paramemter['Password']!=""){
			$result = $this->user_model->login($paramemter);
			if($result['ret']==200)
			{
			
				$data['code'] = 0;
				$data['UserInfo']=$result['UserInfo'];

			}else{
				$data['code']=2;
				$data['message'] = "UserName or Password not Match";

			}
		}
		$content_data['display_value']['Link']="http://i.cs.hku.hk/~zqshi/ci/index.php/Server/loginM";
		
		$content_data['display_value']['Input']=json_encode($json_string, true);

		$content_data['display_value']['Return']=json_encode($data, true);

		$this->load->view('result',$content_data);

	}

	public function loginM()
	{
		$json_string = $this->input->raw_input_stream;
	
		$this->load->model('user_model','',true);
		//传过来的是JSON String，用下面这句

    	$json = json_decode($json_string, true);
    	
    	//传过来的是JSON Object，用下面这句
		//$json = $json_string;
		$paramemter['UserName'] = $json['UserName'];
        $paramemter['Password'] = md5($json['Password']);
        $data['code']="1";
        if($paramemter['UserName']!=""&&$paramemter['Password']!=""){

			$result = $this->user_model->login($paramemter);
			if($result['ret']==200)
			{
			
				$data['code'] = 0;
				$data['UserInfo']=$result['UserInfo'];
				$this->output
	        			->set_content_type('application/json')
	        			->set_output(json_encode($data));
			}else{
				$data['code']=2;
				$data['message'] = "UserName or Password not Match";
				$this->output
	        			->set_content_type('application/json')
	        			->set_output(json_encode($data));
			}
		}
	}
}
