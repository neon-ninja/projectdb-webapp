<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<title>Center for E-research Projects</title>
</head>
<body>
<?php

// This file acts as a frontend to the Projects Database
// By Nick Young

include ('config.php');

// Handles errors. Prints a message to the user with an email link, where the subject contains an error code to pinpoint the problem
function error($errorcode) {
  print "Something has gone wrong. Please contact the <a href='mailto:$adminEmail?Subject=projectdb phpapp $errorcode'>administrator</a>";
  die;
}

$db = new mysqli($host, $user, $pass, $database, $port);
if ($db->connect_errno) {
    //echo "Failed to connect to MySQL: (" . $db->connect_errno . ") " . $db->connect_error;
    error('db1');
}

if (!isset($_GET['inst'])) {
  echo '<div class="header"><h1>Current Projects</h1></div>';
  // Print institutions (UOA, UOC etc)
  if ($institutions = $db->query("SELECT DISTINCT hostInstitution FROM project ORDER BY hostInstitution")) {
    while ($row = $institutions->fetch_row()) {
      $i = $row[0];
      if (in_array($i,$blacklist)) continue;
      echo "<div class='instBlock'><div style='margin:15px'><a href='?inst=$i'><h2 style='display:inline'>$i</h2></a></div>";
      // Then print departments under that institution
      if ($departments = $db->query("SELECT DISTINCT department1 FROM researcher WHERE institution='$i' ORDER BY department1")) {
        // If there's a result, enumerate through it
        while ($row = $departments->fetch_row()) {
          $d = $row[0];
          if ($d!='') {
            print "<a href='?inst=$i&dept=$d'>$d</a><br/>";
          }
        }
        echo '</div>';
      } else {
        error('db2');
      }
    }
  }
} else {
  // Protect against SQL injection
  // Use these to filter out non-productive projects
  // INNER JOIN researchoutput ro ON project.id=ro.projectId
  // INNER JOIN project_kpi pk ON project.id=pk.projectId
  $i = $db->real_escape_string($_GET['inst']);
  $deptCondition='';
  $d = '';
  // Specifying a department is optional
  if (isset($_GET['dept'])) {
    $d = $db->real_escape_string($_GET['dept']);
    $deptCondition = " AND r.department1='$d'";
  }
  
  print "<div class='header'>
  <a href='/'><button class='backButton'>Back</button></a>
  <h1>$i $d</h1>
  </div>";
  // Select recent project ids where the PI is from this dept
  if ($projects = $db->query("SELECT DISTINCT project.id FROM project
                              INNER JOIN researcher_project rp ON rp.projectId=project.id
                              INNER JOIN researcher r ON r.id=rp.researcherId AND rp.researcherRoleId=1
                              INNER JOIN project_facility pf ON project.id=pf.projectId AND (pf.facilityId=1 OR pf.facilityId=5)
                              WHERE (project.endDate IS NULL OR project.endDate='' OR project.endDate>CURDATE())
                              AND hostInstitution='$i'$deptCondition")) {
    if ($projects->num_rows==0) {
      print "No projects";
      die;
    }
    // If there's a result, enumerate through it
    while ($row = $projects->fetch_row()) {
      $p = $row[0];
      if ($row = $db->query("SELECT name,description FROM project WHERE id=$p")->fetch_assoc()) {
        $title = $row['name'];
        $description = $row['description'];
        if (!$title || !$description) continue;
        if ($researchers = $db->query("SELECT researcher.fullName, pictureUrl, rr.name as role FROM researcher
                                       INNER JOIN researcher_project rp ON researcher.id=rp.researcherId AND rp.projectId=$p
                                       INNER JOIN researcherrole rr ON rr.id=rp.researcherRoleId")) {
          $researcherNames = array();
          $pictures = array();
          
          while ($row = $researchers->fetch_assoc()) {
            $img = '';
            // Skip default images
            if (!strstr($row['pictureUrl'],'avatar')) {
              $size = 150;
              $img = "<img src='".$row['pictureUrl']."' title='".$row['fullName']."' alt='".$row['fullName']."' height='$size'>";
            }
            if ($row['role']=='PI') {
              // PI's names and photos get printed first
              array_unshift($researcherNames,$row['fullName']);
              array_unshift($pictures,$img);
            } else {
              $researcherNames[] = $row['fullName'];
              $pictures[] = $img;
            }
          }
          $researcherNames = implode(', ',$researcherNames);
          $pictures = implode('',$pictures);
        }
        $outputsString = '';
        if ($outputs = $db->query("SELECT rot.name as type,description FROM researchoutput ro
                                   INNER JOIN researchoutputtype rot ON ro.typeId=rot.id
                                   WHERE projectId=$p ORDER BY typeId")) {
          while ($row = $outputs->fetch_assoc()) {
            $outputsString.=sprintf("<li>%s: %s</li>\n",$row['type'],$row['description']);
          }                     
        }
        $kpiString = '<i>';
        if ($kpis = $db->query("SELECT notes FROM project_kpi WHERE projectId=$p")) {
          while ($row = $kpis->fetch_row()) {
            $kpiString.=$row[0].'<br/><br/>';
          }
        }
        $kpiString.='</i>';
        // Output results as a table
        
        print "<table cellspacing=0 cellpadding=10>
               <tr>
               <th style='width:80%'><h2>$title</h2></th>
               <th style='width:20%'>$researcherNames</th>
               </tr>
               <tr>
               <td style='width:80%'>$description</td>
               <td style='width:20%;text-align:center'>$pictures<br/><br/>$kpiString</td>
               </tr>
               <tr class='outputs'>
               <td colspan=2>
               <ul>
               $outputsString
               </ul>
               </td>
               </tr>
               </table>
               <br/><br/>";
      }
    
    }
  } else {
    error('q1');
  }
}

?>
</body>
</html>