import glob
import csv

dictCSV = ".\\androidx-class-mapping.csv"
projectPath = "."

def replace_all(text, dic):
    for i, j in dic.items():
        text = text.replace(i, j)
    return text

with open(dictCSV, mode='r') as infile:
    reader = csv.reader(infile)
    replaceDict = {rows[0]:rows[1] for rows in reader}

files = []
for ext in ('/**/*.xml', '/**/*.kt', '/**/*.java'):
   files.extend(glob.iglob(projectPath + ext, recursive=True))

for filename in files:
    print("Replacing in file: " + filename)
    try:
        with open(filename, 'r') as file :
            filedata = file.read()

        filedata = replace_all(filedata, replaceDict)
        
        with open(filename, 'w') as file:
            file.write(filedata)
    except Exception as e:
        print("Error reading/writing file. Skipping ...")