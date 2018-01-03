var express = require('express');
var SolrNode = require('solr-node');
var SolrClient = require('solr-client');
var fs = require('file-system');
var csv = require('fast-csv');

var app = express();
var bodyParser = require('body-parser');
var sockets = require('sockets');
var stream = fs.createReadStream("mapNYTimesDataFile.csv");

var csvObjFileUrl = {};
var csvObjUrlFile = {}; 

global.pageRank=0;
global.masterObj = new Object;
global.masterObj.results=[];





var csvStream = csv()
    .on("data", function(data){
         csvObjFileUrl[data[0]]=data[1];
         csvObjUrlFile[data[1]]=data[0];
    })
    .on("end", function(){
//         console.log("done");
		
    });
 
stream.pipe(csvStream);

app.use(express.static(__dirname+"/public"));
app.use(bodyParser.json());
app.use("/node_modules", express.static('node_modules'));        

var client1 = new SolrNode({
	host:'127.0.0.1',
	port:'8983',
	core:'Indexes',
	protocol:'http'
});

var client2 = new SolrNode({
	host:'127.0.0.1',
	port:'8983',
	core:'suggestionEngine',
	protocol:'http'
});





function getSnippets(s,doc){
	var spawnSync = require('child_process').spawnSync;	
	//var spawnSync = require('exec-sync');
	//var result = spawnSync('python',['/home/amitd92/Desktop/Git/SearchEngine/extract.py',s,doc]);
	var result = spawnSync('python', ['/home/amitd92/Desktop/Git/SearchEngine/extract.py',s,doc],{stdio:'pipe'});
	//var result = spawnSync('python /home/amitd92/Desktop/Git/SearchEngine/extract.py trump "/home/amitd92/solr/NYTimesData/NYTimesDownloadData/34c35475-2646-41ba-a4f9-eb4e364ce584.html"');
	//console.log("python /home/amitd92/Desktop/Git/SearchEngine/extract.py "+s+" "+doc)
	//var user = execSync("echo|python /home/amitd92/Desktop/Git/SearchEngine/extract.py "+s+" "+doc);
	console.log(result.output[1].toString('utf8'));
	
	
	return result.output[1].toString('utf8');
};

app.post('/updatePageRank',function(req,res){str(extract(syss,docs)[0])
	var obj = new Object;
	if(req.body.check.value=='YES'){
		global.pageRank=1;
		obj.value="Activated";
		
	}else{
		global.pageRank=0;
		obj.value="Deactivated";
	}
	res.json(obj);
});
	
app.post('/suggest',function(req,res){
	

	client2.SEARCH_PATH="suggest";
	var ss = "http://localhost:8983/solr/suggestionEngine/suggest?indent=on&spellcheck.q="+req.body.searchText+"&spellcheck=on&wt=json"
	//var origQuery = client2.query().q(req.body.searchText);
	origQuery=ss;
	client2.search(origQuery,function (err, result) {
	   		if (err) {
	    	  console.log(err);
	    	  return;
		    }
			else
			{
				var x = JSON.parse(result);
				if (typeof x.spellcheck != 'undefined')
				{
					if (typeof x.spellcheck.suggestions != 'undefined'){	
						if (x.spellcheck.suggestions.length>1){
							var s = x.spellcheck.suggestions[1]["suggestion"];
							
						}
					}
				}
				
				//console.log(x.spellcheck["suggestions"]);	
				res.json(s);
			}
	});
	return;
});


app.post('/form',function(req,res){
	
	//handle python
	
	var spawn1 = require("child_process").spawn;
	
	
	if ((typeof req.body.searchtext=='undefined') || req.body.searchtext==''){
		res.json(400);
		return;
	}
	var process1 = spawn1('python',["/home/amitd92/Desktop/Git/SearchEngine/one.py",req.body.searchtext]);

	process1.stdout.on('data', function (data){
	// Do something with the data returned from python script
		
		var textChunk = data.toString('utf8');
		textChunk = textChunk.replace("\n","").trim();
		global.textChunk = textChunk;
		var origQuery = client1.query().q(req.body.searchtext);
		var suggQuery = client1.query().q(textChunk);
		
		var origFlag=true;
		console.log(textChunk);
		if (req.body.mode =="mainSubmit"){
			debugger;
			if (textChunk.toLowerCase()!=req.body.searchtext.toLowerCase()){
				var strQuery = suggQuery;	
				origFlag=false;
			}else{
				var strQuery = origQuery;
				origFlag=true;
			}
		}else if(req.body.mode =="didyouMean"){
			var strQuery = suggQuery;
			origFlag=false;	
		}else{
			
			var strQuery = origQuery;
			origFlag=true;
		}

		global.origFlag = origFlag;
		if (global.pageRank==1){
			strQuery = strQuery.sort({
			pageRankFile :'desc'
			});
		}
		
		var no;
	
		client1.search(strQuery,function (err, result) {
	   		if (err) {
		    	  console.log(err);
		    	  return;
		    	}
			else
			{   
				 
				if(result.response["numFound"]=='0'){
					
						//res.json(0);
						//console.log(result.spellcheck);
						var obj1 = new Object;
						obj1.spellcheck=global.textChunk;
						obj1.NumberofResultsFound="0";
						obj1.qtime = "0";
						var titles=[];
						if (global.origFlag){
							obj1.qtype = "orig";
						}else{
							obj1.qtype = "sugg";
						}
						titles.push(obj1);
						res.json(titles);
						return;
					
				}
				var obj1 = new Object();
				var titles = [];
				obj1.spellcheck = global.textChunk;
				//console.log(result.response.numFound.toString());
				obj1.NumberofResultsFound = result.response.numFound.toString();
				obj1.qtime = result.responseHeader.QTime;
				if (global.origFlag){
					obj1.qtype = "orig";
				}else{
					obj1.qtype = "sugg";
				}

				var docs = result.response.docs;
				titles.push(obj1);
				var k=1;
				var t ="";
				for(var key in docs){
					var obj = new Object;
					var i = docs[key].id.length-1;
					var s ='';
					while((docs[key].id)[i]!='/'){
						s = (docs[key].id)[i]+s;
						i=i-1;
					}
					obj.url =csvObjFileUrl[s];
					obj.index = k;
					k+=1;
					obj.id=docs[key].id;
					
					
					t = docs[key].id;
					if (origFlag)	
						var lst = getSnippets(req.body.searchtext,t);
					else
						var lst = getSnippets(global.textChunk,t);
					//	console.log(lst);
					obj.snippets = lst;	
					obj.title = docs[key].title.toString();
					if (docs[key].description != null){
						obj.desc = docs[key].description.toString();
					}
					else{
						obj.desc = "No Desc Found for the result";
					}
					
					
					//console.log(snippets);
					titles.push(obj);
				}
				
				//console.log(getSnippets(req.body.searchtext,t));
				res.json(titles);
				
			}
		});
	});
});


//process.env.PORT ||
app.listen( 3000);

console.log("server running on port 3000");



